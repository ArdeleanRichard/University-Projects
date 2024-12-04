import pandas as pd

from utils.log_reg import LogisticRegresser
from utils.preproc import standard_scaler, train_test_split
from utils.validation import binary_classification_report

# Dataset from: https://www.kaggle.com/datasets/mlg-ulb/creditcardfraud?select=creditcard.csv
df = pd.read_csv("./data/creditcard.csv")
print(df.columns)
print(df.head())

# X = df.iloc[:, 1:-1].to_numpy() # without 'Time' feature
X = df.iloc[:, :-1].to_numpy()
y = df.iloc[:, -1].to_numpy()

print(X.shape)
print(y.shape)

# Data scaling ('Amount' feature is larger than the others, 'Time' feature is increasing)
X = standard_scaler(X) # implemented in 'preproc.py'

X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=0.8) # implemented in 'preproc.py'

model = LogisticRegresser(X_train, y_train)
print("\n" * 3)
print(f"[*] Logistic Regression")
model.fit()

y_pred = model.predict(X_test)

performance_dict = binary_classification_report(y_test, y_pred)
print(performance_dict)

