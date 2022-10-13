<?php 
require "ccconn.php";
$id = $_POST["id"];
$maxcalories = $_POST["maxcalories"];

$mysql_qry = "UPDATE consultant SET maxcalories='$maxcalories' WHERE id='$id';";

$my_qry = "select maxcalories from consultant WHERE id='$id';";


if($conn->query($mysql_qry) === TRUE) {
$result = mysqli_query($conn ,$my_qry);
$row = mysqli_fetch_array($result,MYSQLI_NUM);
if(mysqli_num_rows($result) > 0) {
echo "$row[0]";
}
else {
echo "NoSuchFood";
}
}
else 
{
	echo "UpdateFail";
}
$conn->close();
 
?>