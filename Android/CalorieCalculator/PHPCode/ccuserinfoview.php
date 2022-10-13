<?php 
require "ccconn.php";
$id = $_POST["id"];
$mysql_qry = "select gender,weight,height,age,exercise,goal from user where id='$id';";
$result = mysqli_query($conn ,$mysql_qry);
$row = mysqli_fetch_array($result,MYSQLI_NUM);


if(mysqli_num_rows($result) > 0) {
	
	echo "ViewSuccess $row[0] $row[1] $row[2] $row[3] $row[4] $row[5]";
}
else 
{
	echo "ViewFail";
}


?>