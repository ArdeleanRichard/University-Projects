<?php 
require "ccconn.php";
$id = $_POST["id"];
$name = $_POST["name"];
$grams = $_POST["grams"];

$add_qry = "select calories from food where name like '$name';";
$res = mysqli_query($conn ,$add_qry);
$r = mysqli_fetch_array($res);

$mysql_qry = "UPDATE consultant SET daycalories=daycalories+'$r[0]'*'$grams'/100 WHERE id='$id';";

$my_qry = "select daycalories from consultant WHERE id='$id';";

$insert_qry= "insert into day (userid, name, calories,admin) values ('$id', '$name', $r[0]*$grams/100, 1)";
$insert_res = mysqli_query($conn ,$insert_qry);


if(mysqli_num_rows($res) > 0) {
if($conn->query($mysql_qry) === TRUE) {
	$result = mysqli_query($conn ,$my_qry);
	$row = mysqli_fetch_array($result,MYSQLI_NUM);
	if(mysqli_num_rows($result) > 0) {
		echo "$row[0]";
	}
}
else 
{
	echo "UpdateFail";
}
}
else 
{
echo "NoSuchFood";
}
$conn->close();
$conn->close();
 
?>