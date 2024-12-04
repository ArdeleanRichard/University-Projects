import pandas as pd
from sklearn import preprocessing
from sklearn.ensemble import AdaBoostClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, classification_report
from sklearn.model_selection import train_test_split
from sklearn.neural_network import MLPClassifier
from sklearn.svm import SVC
from sklearn.tree import DecisionTreeClassifier

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
scaler = preprocessing.StandardScaler().fit(X)
X = scaler.transform(X)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)


models = {
    "Logistic Regression": LogisticRegression(max_iter=1000),
    "Decision Tree": DecisionTreeClassifier(),
    # The “balanced” mode uses the values of y to automatically adjust weights inversely proportional to class frequencies in the input data as n_samples / (n_classes * np.bincount(y)).
    "SVM": SVC(kernel='linear', class_weight='balanced'),
    "Neural Network (MLP)": MLPClassifier(hidden_layer_sizes=(100,), max_iter=1000),
    "AdaBoost": AdaBoostClassifier(n_estimators=50),
}



metrics = {}
for model_name, model in models.items():
    print("\n" * 3)
    print(f"[*] {model_name}")
    model.fit(X_train, y_train)

    y_pred = model.predict(X_test)

    # Performance Option 1
    print(classification_report(y_test, y_pred))

    # Performance Option 2
    print("\n")
    print(f"[###] Accuracy: {accuracy_score(y_test, y_pred)}")
    print(f"[###] Precision: {precision_score(y_test, y_pred, average='macro')}")
    print(f"[###] Recall: {recall_score(y_test, y_pred, average='macro')}")
    print(f"[###] F1 Score: {f1_score(y_test, y_pred, average='macro')}")





