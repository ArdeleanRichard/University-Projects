def binary_classification_report(y_true, y_pred):
    # count true positives, false positives, true negatives, and false negatives
    tp = fp = tn = fn = 0
    for true, pred in zip(y_true, y_pred):
        if pred == True:
            if true == True:
                tp += 1
            else:
                fp += 1
        else:
            if true == False:
                tn += 1
            else:
                fn += 1

    # calculate precision and recall
    precision = tp / (tp + fp) if (tp + fp) > 0 else 0
    recall = tp / (tp + fn) if (tp + fn) > 0 else 0

    # calculate f1 score
    fscore = 2 * precision * recall / (precision + recall) if (precision + recall) > 0 else 0

    # calculate accuracy
    accuracy = (tp + tn) / len(y_true)

    return {
        "precision": precision,
        "recall": recall,
        "f1-score": fscore,
        "accuracy": accuracy,
    }

