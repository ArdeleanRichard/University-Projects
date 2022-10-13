<?php 
require "ccconn.php";
$id = $_POST["id"];
$maxcalories = $_POST["maxcalories"];

$mysql_qry = "UPDATE user SET maxcalories='$maxcalories'-250 WHERE id='$id';";

$my_qry = "select maxcalories from user WHERE id='$id';";


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