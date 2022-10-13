<?php 
require "ccconn.php";


$id=$_POST["id"];
$answer=$_POST["answer"];

$mysql_qry = "UPDATE message SET answer='$answer' WHERE id='$id';";


if($conn->query($mysql_qry) === TRUE) {
	echo "AnswerSuccess";
}
else 
{
	echo "AnswerFail";
}
$conn->close();
 
?>