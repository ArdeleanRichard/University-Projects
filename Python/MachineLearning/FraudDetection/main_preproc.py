import pandas as pd
from matplotlib import pyplot as plt
from sklearn.decomposition import PCA
import seaborn as sns

# Dataset from: https://www.kaggle.com/datasets/mlg-ulb/creditcardfraud?select=creditcard.csv
df = pd.read_csv("./data/creditcard.csv")
y = df.iloc[:, -1].to_numpy()
print(df.columns)
print(df.head())

# Data types and null check
print("\nData info:")
print(df.info())

print("\nMissing values per column:")
missing_values = df.isnull().sum()
print(missing_values[missing_values > 0])  # Display only columns with missing values

# Get a statistical summary of numerical features
print("\nData stats:")
print(df.describe())

# Correlation mat - the features are not highly correlated, the highest value (-0.5), all should be used
corr_matrix = df.corr()
plt.figure(figsize=(14, 10))
sns.heatmap(corr_matrix, annot=False, cmap="coolwarm", linewidths=0.5)
plt.title("Correlation Matrix of All Features")
plt.show()



X = df.iloc[:, :-1].to_numpy()
# Dimensionality Reduction with PCA
pca = PCA(n_components=10)
X_pca = pca.fit_transform(X)

# Plot cumulative explained variance - 1 component already has 99.97% explained variance, from 2+ there is 99.99%
explained_variance = pca.explained_variance_ratio_.cumsum()
print(explained_variance)
plt.plot(range(1, len(explained_variance) + 1), explained_variance, marker='o')
plt.title("Cumulative Explained Variance by PCA Components")
plt.xlabel("Number of Components")
plt.ylabel("Cumulative Explained Variance")
plt.grid()
plt.show()