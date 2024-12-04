import pandas as pd
from sklearn import preprocessing
from sklearn.ensemble import AdaBoostClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, classification_report, make_scorer
from sklearn.model_selection import train_test_split, GridSearchCV
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
# TODO De testat, scos timp/ shuffle la date, split stratificat, minmax normalization
scaler = preprocessing.StandardScaler().fit(X)
X = scaler.transform(X)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# Define models and parameter grids
models = {
    "Logistic Regression": {
        "model": LogisticRegression(max_iter=1000),
        "params": {
            "C": [0.01, 0.1, 1, 10],
            "solver": ['lbfgs', 'liblinear'],
            "class_weight": [None, 'balanced']
        }
    },
    "Decision Tree": {
        "model": DecisionTreeClassifier(),
        "params": {
            "max_depth": [None, 10, 20, 30],
            "min_samples_split": [2, 5, 10],
            "class_weight": [None, 'balanced']
        }
    },
    # "SVM": {
    #     "model": SVC(kernel='linear'),
    #     "params": {
    #         "C": [0.1, 1, 10],
    #         "class_weight": [None, 'balanced']
    #     }
    # },
    "Neural Network (MLP)": {
        "model": MLPClassifier(max_iter=1000),
        "params": {
            "hidden_layer_sizes": [(50,), (100,), (100, 100)],
            "activation": ['tanh', 'relu', 'sigmoid'],
            "solver": ['adam', 'sgd']
        }
    },
    "AdaBoost": {
        "model": AdaBoostClassifier(),
        "params": {
            "n_estimators": [50, 100, 200],
            "learning_rate": [0.01, 0.1, 1],
            "algorithm": "SAMME", # default value results in futurewarning: SAMME.R
        }
    }
}


scoring = {
    'accuracy': 'accuracy',
    'precision': make_scorer(precision_score, average='macro'),
    'recall': make_scorer(recall_score, average='macro'),
    'f1': make_scorer(f1_score, average='macro')
}


all_results = []

for model_name, model_data in models.items():
    print(f"\n GridSearchCV for {model_name}...")

    grid_search = GridSearchCV(model_data["model"], model_data["params"], scoring=scoring, refit='accuracy', cv=5, n_jobs=-1, verbose=1, return_train_score=True)
    grid_search.fit(X_train, y_train)

    grid_results = pd.DataFrame(grid_search.cv_results_)
    grid_results['Model'] = model_name


    all_results.append(grid_results)

    best_params = grid_search.best_params_
    best_score = grid_search.best_score_

    print(f"Best parameters for {model_name}: {best_params}")
    print(f"Best cross-validation accuracy for {model_name}: {best_score}")


all_results_df = pd.concat(all_results, ignore_index=True)
columns_to_save = [
    'Model', 'param_C', 'param_solver', 'param_class_weight',  # Logistic Regression params
    'param_max_depth', 'param_min_samples_split',  # Decision Tree params
    'param_n_estimators', 'param_learning_rate',  # AdaBoost params
    'param_hidden_layer_sizes', 'param_activation', # 'param_solver',  # MLP params
    # 'param_C',  # SVC params
    'mean_train_accuracy', 'std_train_accuracy',  # Accuracy on training set
    'mean_train_precision', 'std_train_precision',  # Precision on training set
    'mean_train_recall', 'std_train_recall',  # Recall on training set
    'mean_train_f1', 'std_train_f1'  # F1 Score on training set
    'mean_test_accuracy', 'std_test_accuracy',  # Accuracy on test set
    'mean_test_precision', 'std_test_precision',  # Precision on test set
    'mean_test_recall', 'std_test_recall',  # Recall on test set
    'mean_test_f1', 'std_test_f1',  # F1 Score on test set
]

all_results_df.to_csv("grid_search_full_results_with_metrics.csv", index=False, columns=columns_to_save)
print(all_results_df[columns_to_save].head())