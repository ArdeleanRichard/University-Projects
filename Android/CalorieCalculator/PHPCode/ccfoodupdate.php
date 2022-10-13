<?php 
require "ccconn.php";

$id=$_POST["id"];
$name = $_POST["name"];
$calories = $_POST["calories"];

$mysql_qry = "UPDATE food SET name='$name', calories='$calories' WHERE id='$id';";


if($conn->query($mysql_qry) === TRUE) {
	echo "UpdateSuccess";
}
else 
{
	echo "UpdateFail";
}
$conn->close();
 
?>