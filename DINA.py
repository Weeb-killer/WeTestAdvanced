import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
#from tqdm import tqdm
from itertools import product
import matplotlib
#from psy import EmDina, MlDina
#from sklearn.metrics import accuracy_score, f1_score
#from sklearn.metrics import r2_score, explained_variance_score, mean_absolute_error
import csv

plt.rcParams['font.sans-serif'] = ['SimHei']  # 步骤一（替换sans-serif字体）
plt.rcParams['axes.unicode_minus'] = False  # 步骤二（解决坐标轴负数的负号显示问题）


# 依据知识点组合和试题知识点分布，计算理想情况下，各知识点组合的答题情况
def compute_eta(Q, A):
    # 计算各个试题需要的知识点数量
    kowns = np.sum(Q * Q, axis=0)
    # 计算各个知识点组合与试题交叉的知识点数量
    cross = np.dot(A, Q)
    # 初始化理想情况下答题结果矩阵
    eta = np.ones(shape=(A.shape[0], Q.shape[1]))
    # 如果知识点组合与试题交叉的知识点数量小于试题所需的知识点数量，则无法回答正确，否则能回答正确
    eta[cross < kowns] = 0
    # 返回理想情况下答题结果矩阵
    return eta


# 计算加入试题猜对率和失误率后，各个试题答对的概率
def compute_propa(eta, s, g):
    # eta[i,j] = 0时，试题答对的概率等于试题猜对率g_j
    # eta[i,j] = 1时，试题答对的概率等于1减去试题失误率s_j
    propa = (g ** (1 - eta)) * ((1 - s) ** eta)
    propa[propa <= 0] = 1e-10
    propa[propa >= 1] = 1 - 1e-10

    # 返回加入试题猜对率和失误率后，各个试题答对的概率
    return propa


def get_priors(A_all, p_know, p_know_list):
    # 计算平均分布的先验分布
    prior1 = np.ones(A_all.shape[0]) / A_all.shape[0]
    # 计算多项式分布的先验概率
    # 各知识点掌握概率相同的
    prior2 = np.ones(A_all.shape[0])
    prior3 = np.ones(A_all.shape[0])

    for l in range(A_all.shape[0]):
        for k in range(A_all.shape[1]):
            p = p_know_list[k]
            prior2[l] *= (p_know ** A_all[l, k] * (1 - p_know) ** (1 - A_all[l, k]))
            prior3[l] *= (p ** A_all[l, k] * (1 - p) ** (1 - A_all[l, k]))

    return [prior1, prior2, prior3, None]


# 计算给定答题情况和参数的情况下，学生掌握各个知识点组合的概率
def compute_gamma(X, pi, propa):
    # 这儿使用一个技巧 x = exp(log(x)),将连乘转换成求和，然后用矩阵进行运算
    log_pj = np.log(propa)
    log_qj = np.log(1 - propa)
    log_pi = np.log(pi)

    # 计算各个学生掌握这种知识点组合的置信
    gamma = np.exp(np.dot(X, log_pj.T) + np.dot((1 - X), log_qj.T) + log_pi)
    # 计算各个学生的总置信
    gamma_sum = np.sum(gamma, axis=1)
    # 进行归一化，计算各个学生掌握各种知识点组合的概率
    gamma = (gamma.T / gamma_sum).T
    # 返回学生掌握各个知识点组合的概率
    return gamma


# 评估各个参数的值
def compute_theta(X, gamma, eta):
    # 获取不足以答题试题的知识状态
    I0 = np.dot(gamma, 1 - eta)
    # 获取能答对试题的知识状态
    I1 = np.dot(gamma, eta)

    # 计算不足以答对试题时却答对的期望
    R0 = I0 * X
    # 计算足以答对试题时，答对的期望
    R1 = I1 * X

    I0 = np.sum(I0, axis=0)
    I1 = np.sum(I1, axis=0)
    R0 = np.sum(R0, axis=0)
    R1 = np.sum(R1, axis=0)

    I0[I0 <= 0] = 1e-15
    I1[I1 <= 0] = 1e-15

    # 更新猜对率和失误率
    g = R0 / I0
    s = (I1 - R1) / I1

    # 更新知识状态分布概率
    pi = np.sum(gamma, axis=0) / gamma.shape[0]
    pi[pi <= 0] = 1e-15
    pi[pi >= 1] = 1 - 1e-15

    return pi, s, g


# 使用EM算法对模型中各个参数进行评估
# 若未传入知识状态的先验概率，则动态估计知识状态概率
def em(X, Q, maxIter=1000, tol=1e-3, prior=None):
    n_stu = X.shape[0]
    n_qus = X.shape[1]
    n_kno = Q.shape[0]
    # print(n_qus,n_kno)

    g = np.random.random(n_qus) * 0.25
    s = np.random.random(n_qus) * 0.25

    # 获取所有可能的知识点组合
    A_all = np.array(list(product([0, 1], repeat=n_kno)))

    # 如果传入知识状态先验概率，则使用用户传入的先验概率
    # 否则使用动态估计的知识状态概率
    if prior is None:
        pi = np.ones(shape=(A_all.shape[0])) / A_all.shape[0]
    else:
        pi = prior

    # 循环迭代，进行E步和M步
    for t in range(maxIter):

        # 计算理想情况下，各种知识状态的答题情况
        eta = compute_eta(Q, A_all)
        # 加入猜对率和失误率后，各种知识状态的答题情况
        propa = compute_propa(eta, s, g)
        # E步计算期望
        gamma = compute_gamma(X, pi, propa)
        # M步更新模型参数
        pi_t, s_t, g_t = compute_theta(X, gamma, eta)

        # 计算各个参数更新的绝对大小
        update = max(np.max(np.abs(pi_t - pi)), np.max(np.abs(g_t - g)), np.max(np.abs(s_t - s)))
        if update < tol:
            return pi_t, g_t, s_t, gamma

        # 更新参数准备下一轮迭代
        if prior is None:
            pi = pi_t
        g = g_t
        s = s_t

    #     print("step %d update %.8f" % (t, update))

    return pi, g, s, gamma


# 评估学生的知识状态
def solve(gamma, n_kownlege):
    A_all = np.array(list(product([0, 1], repeat=n_kownlege)))
    A_idx = np.argmax(gamma, axis=1)
    return A_all[A_idx], A_idx


# 计算全数据的联合概率对数似然
def joint_loglike(X, Q, s, g, pi):
    A_all = np.array(list(product([0, 1], repeat=Q.shape[0])))
    eta = compute_eta(Q, A_all)
    propa = compute_propa(eta, s, g)
    log_pj = np.log(propa)
    log_qj = np.log(1 - propa)
    log_pi = np.log(pi)
    # log[(P(x_i |a_u)P(a_u)]
    L = np.dot(X, log_pj.T) + np.dot((1 - X), log_qj.T) + log_pi
    # P(a_u |x_i)log[(P(x_i |a_u)P(a_u)]
    L = L * gamma
    return np.sum(L)


# 计算得分数据(边缘概率)对数似然
def marginal_loglike(X, Q, s, g, pi):
    A_all = np.array(list(product([0, 1], repeat=Q.shape[0])))
    eta = compute_eta(Q, A_all)
    propa = compute_propa(eta, s, g)
    gamma = compute_gamma(X, pi, propa)
    log_pj = np.log(propa)
    log_qj = np.log(1 - propa)
    log_pi = np.log(pi)

    # P(X_i|alpha_u)p(alpha_u)
    L = np.exp(np.dot(X, log_pj.T) + np.dot((1 - X), log_qj.T) + log_pi)
    # Nx1 sum_u P(X_i|alpha_u)p(alpha_u)
    L = np.sum(L, axis=1)
    # sum_i{log[ sum_u P(X_i|alpha_u)p(alpha_u)]}
    L = np.log(L)
    return np.sum(L)



# %%
data_dir = "./data/"
# df_frac20X = pd.read_csv(data_dir + "frac20X.csv")
# df_frac20Q = pd.read_csv(data_dir + "frac20Q.csv")
df_frac20X = pd.read_csv(data_dir + "b.csv")
df_frac20Q = pd.read_csv(data_dir + "a.csv")
X = df_frac20X.values
Q = df_frac20Q.values.T

A_all = np.array(list(product([0, 1], repeat=Q.shape[0])))
priors = get_priors(A_all, p_know=0.7, p_know_list=[0.1 ,0.2 ,0.3 ,0.4 ,0.5 ,0.6 ,0.7 ,0.8,0.9,1])
d = [ 2 *Q.shape[1] , 2 *Q.shape[1] , 2 *Q.shape[1] , 2 *Q.shape[1] + 2** Q.shape[0]]

ss = []
gs = []
NLL = []
AIC = []
gamma_arr = []
# for i in range(4):
#     pi_t, g_t, s_t, gamma = em(X, Q, maxIter=500, tol=1e-6, prior=priors[i])
pi_t, g_t, s_t, gamma = em(X, Q, maxIter=500, tol=1e-6, prior=None)
ss.append(s_t)
gs.append(g_t)
gamma_arr.append(gamma)

# 联合概率对数似然
# LL = joint_loglike(X, Q, s_t, g_t, pi_t)
#     # 边缘概率对数似然
#     LL = marginal_loglike(X,Q,s_t,g_t,pi_t)

# NLL.append(-LL)
# AIC.append(-2 * LL + d[3])
# print(NLL, AIC)
# print('gamma:', gamma_arr)
# print('gamma-shape:', np.array(gamma_arr).shape)
k_n=Q.shape[0]
A_alls, A_idx = solve(gamma_arr[0],k_n)
print(A_alls, A_idx)
print(A_alls.shape)
#每次答题的知识点掌握率

howmany=0
for j in range(len(A_alls)):
    for i in range(len(A_alls[0])):
        howmany+=A_alls[j][i]
# print(howmany/(len(A_alls[0])*len(A_alls)))
howmany=howmany/(A_alls.size)

fornow=0
myarr=[]
for j in range(8):
    fornow=0
    fornow+=(A_alls[0][j]/8)
    myarr.append(fornow)
#总知识点掌握饼状图
namearr=[]
with open(data_dir + "a.csv",'r') as f:
    reader=csv.reader(f)
    namearr=list(reader)[0]
for i in range(8):
    if(A_alls[0][i]==0):
        namearr[i]="none"
# print(namearr)
plt.rcParams['font.sans-serif'] = ['Microsoft YaHei']
arr=[howmany,(1-howmany)]
plt.pie(arr,labels=['掌握知识点', '未掌握知识点'],autopct="%1.1f%%",explode=[0.1,0],wedgeprops={'width':0.2, 'edgecolor':'w'},pctdistance=1.5,shadow=True)
plt.title("知识点掌握情况")
plt.pie(myarr,labels=namearr,pctdistance=0.5, radius=0.8, autopct='%3.1f%%',shadow=True)
plt.axis('equal')
plt.savefig("总知识点.png",dpi=300)
plt.show()