import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA
from sklearn.metrics import classification_report, make_scorer, recall_score, f1_score, precision_score, accuracy_score
from sklearn.model_selection import train_test_split, ShuffleSplit, cross_val_score, cross_validate, StratifiedKFold, \
    StratifiedShuffleSplit
from sklearn.preprocessing import StandardScaler, OneHotEncoder, OrdinalEncoder

from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC


dataframe = pd.read_csv("MergeConflictsDataset.csv")

# DATA ANALYSIS
print("------------------ DATA ANALYSIS ---------------------")
print(dataframe.shape)
print(dataframe.head())
print(dataframe.describe())
print(dataframe.info())
print(dataframe.isnull().sum()) # one null found in ancestor => no need to handle missing values, this is the first commit, no parent
# apar null -> scoti din set / media inlocuieste null

# most used encoding is onehotencoder which works well for labels as it transform categorical values into vectors of 0 and 1s
# for the commit, parent1, parent2, ancestor column encoding is required as they are strings
# they should be encoded together as the same values appear and this is relevant to the problem because of the relationship between them
oe = OrdinalEncoder()
dataframe["ancestor"] = dataframe["ancestor"].fillna("first")
data_to_encode = dataframe.to_numpy()[:, :4]
encoded = oe.fit_transform(data_to_encode)




print("------------------ DATA CHECKING ---------------------")
# dataset checking
data = dataframe.to_numpy()
X, y_true = np.hstack((encoded, data[:, 4:-1])), data[:, -1].astype(int)

# check for unbalanced dataset: 0: 25519, 1: 1469 -> heavily unbalanced
print(np.unique(y_true, return_counts=True))

print(X.shape)
print(y_true.shape)


# data visualization
pca = PCA(n_components=2)
pca_features = pca.fit_transform(X)


LABEL_COLOR_MAP = {0: 'white',
                   1: 'red'}

def plot(title, X, labels):
    plt.figure()
    plt.title(title)
    plt.scatter(X[:, 0], X[:, 1], c=[LABEL_COLOR_MAP[l] for l in labels], marker="o", edgecolors='k')
    plt.show()

plot("Data visualization (PCA)", pca_features, y_true)


# for column_name in dataframe.columns.values.tolist()[4:]:
#     sns.histplot(x=column_name, data=dataframe, )
#     plt.show()



# train test splitting -> 80:20
X_train, X_test, y_train, y_test = train_test_split(X, y_true, test_size=0.2, random_state=42)


def classify(classifier, X_train, y_train, X_test, y_test, scale=True):
    if scale == True:
        # data scaling
        X_train = StandardScaler().fit_transform(X_train)
        # test data must be scaled as well, as the classifier learns about scaled data
        X_test = StandardScaler().fit_transform(X_test)

    classifier.fit(X_train, y_train)

    print("---> TRAIN PREDICTION (verify underfitting = low training accuracy)")
    y_pred = classifier.predict(X_train)
    print(classification_report(y_train, y_pred, target_names=['ok', 'conflict']))
    print("---> TEST PREDICTION (verify overfitting = almost perfect training and low testing accuracy)")
    y_pred = classifier.predict(X_test)
    print(classification_report(y_test, y_pred, target_names=['ok', 'conflict']))


print("-----------------------------SVM-----------------------------------")
clf = SVC(class_weight={1: 20})
classify(clf, X_train, y_train, X_test, y_test)

print("-----------------------------DECISION TREES-----------------------------------")
clf = DecisionTreeClassifier()
classify(clf, X_train, y_train, X_test, y_test)


print("-----------------------------RANDOM FOREST-----------------------------------")
clf = RandomForestClassifier()
classify(clf, X_train, y_train, X_test, y_test)


# K-FOLD CROSS VALIDATION with K=5
# 1st variant (using cross_validate function)

# choosing metrics
scoring = { 'acc': 'accuracy',
            'f1': 'f1',
            'f1_macro': 'f1_macro',
            'prec_macro': 'precision_macro',
            'rec_macro': make_scorer(recall_score, average='macro'),
            }

print("------------------------------------------------------------------------------")
print("-----------------------------CROSS VALIDATION---------------------------------")
print("------------------------------------------------------------------------------")

print(":::1st variant (sklearn function):::")
def cross_validation_metrics_using_function(classifier, X, y_true, scoring):
    scores = cross_validate(classifier, X, y_true, cv=5, scoring=scoring, return_train_score=True)
    print("---> Train Scores")
    print(f"Accuracy:   {np.mean(np.array(scores['train_acc']))}")
    print(f"F1:         {np.mean(np.array(scores['train_f1']))}")
    print(f"F1_macro:   {np.mean(np.array(scores['train_f1_macro']))}")
    print(f"Prec_macro: {np.mean(np.array(scores['train_prec_macro']))}")
    print(f"Rec_macro:  {np.mean(np.array(scores['train_rec_macro']))}")
    print("---> Test Scores")
    print(f"Accuracy:   {np.mean(np.array(scores['test_acc']))}")
    print(f"F1:         {np.mean(np.array(scores['test_f1']))}")
    print(f"F1_macro:   {np.mean(np.array(scores['test_f1_macro']))}")
    print(f"Prec_macro: {np.mean(np.array(scores['test_prec_macro']))}")
    print(f"Rec_macro:  {np.mean(np.array(scores['test_rec_macro']))}")


classifier = SVC(class_weight={1: 20})
print("-> SVC")
cross_validation_metrics_using_function(classifier, X, y_true, scoring)
print()

classifier = DecisionTreeClassifier()
print("-> DecisionTree")

cross_validation_metrics_using_function(classifier, X, y_true, scoring)
print()
classifier = RandomForestClassifier()

print("-> RandomForest")
cross_validation_metrics_using_function(classifier, X, y_true, scoring)
print()


print()
print()
print(":::2st variant (random splitting):::")


def cross_validation(clf):
    # stratified ensures relative class distributions
    ss = StratifiedShuffleSplit(n_splits=5, test_size=0.2, random_state=0)
    train_acc = []
    train_f1 = []
    train_prec = []
    train_rec = []
    test_acc = []
    test_f1 = []
    test_prec = []
    test_rec = []

    for train_index, test_index in ss.split(X, y_true):
        X_train = X[train_index]
        y_train = y_true[train_index]
        X_test = X[test_index]
        y_test = y_true[test_index]

        if clf == "SVC":
            classifier = SVC(class_weight={1: 20})
        elif clf == "DecisionTree":
            classifier = DecisionTreeClassifier()
        elif clf == "RandomForest":
            classifier = RandomForestClassifier()


        classifier.fit(X_train, y_train)
        y_pred = classifier.predict(X_train)
        train_acc.append(accuracy_score(y_train, y_pred))
        train_f1.append(f1_score(y_train, y_pred))
        train_prec.append(precision_score(y_train, y_pred))
        train_rec.append(recall_score(y_train, y_pred))

        y_pred = classifier.predict(X_test)
        test_acc.append(accuracy_score(y_test, y_pred))
        test_f1.append(f1_score(y_test, y_pred))
        test_prec.append(precision_score(y_test, y_pred))
        test_rec.append(recall_score(y_test, y_pred))

    print("---> Train Scores")
    print(f"Accuracy:   {np.mean(np.array(train_acc))}")
    print(f"F1:         {np.mean(np.array(train_f1))}")
    print(f"Precision:  {np.mean(np.array(train_prec))}")
    print(f"Recall:     {np.mean(np.array(train_rec))}")

    print("---> Test Scores")
    print(f"Accuracy:   {np.mean(np.array(test_acc))}")
    print(f"F1:         {np.mean(np.array(test_f1))}")
    print(f"Precision:  {np.mean(np.array(test_prec))}")
    print(f"Recall:     {np.mean(np.array(test_rec))}")



print("-> SVC")
cross_validation(clf="SVC")
print()

print("-> DecisionTree")
cross_validation(clf="DecisionTree")
print()

print("-> RandomForest")
cross_validation(clf="RandomForest")
print()

