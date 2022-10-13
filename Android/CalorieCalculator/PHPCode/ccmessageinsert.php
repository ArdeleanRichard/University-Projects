<?php 
require "ccconn.php";
$userid = $_POST["userid"];
$question = $_POST["question"];

$mysql_qry = "insert into message (userid, question, answer) values ('$userid', '$question', NULL)";


if($conn->query($mysql_qry) === TRUE) {
echo "InsertQuestionSuccess";
}
else {
echo "Error: ".$mysql_qry."<br>".$conn->error;
}
$conn->close();
 
?>