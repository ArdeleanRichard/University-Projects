<?php 
require "ccconn.php";
$id=$_POST["id"];
$name = $_POST["name"];
$email = $_POST["email"];
$password = $_POST["password"];
$weight = $_POST["weight"];
$height = $_POST["height"];
$age = $_POST["age"];
$exercise = $_POST["exercise"];

$mysql_qry = "UPDATE consultant SET name='$name', email='$email', password='$password', weight='$weight', height='$height', age='$age', exercise='$exercise' WHERE id='$id';";

$qry = "select gender,weight,height,age,exercise from consultant where id like '$id'";
$result = mysqli_query($conn ,$qry);
$row = mysqli_fetch_array($result,MYSQLI_NUM);

if(strcmp('$row[0]', "Male") || strcmp('$row[0]', "M"))
{
$my_qry = "UPDATE consultant SET maxcalories=9.99*'$row[1]'+6.25*'$row[2]'+4.92*'$row[3]'+'$row[4]'*300/7-161 where id like '$id';";
}
else
{
$my_qry = "UPDATE consultant SET maxcalories=9.99*'$row[1]'+6.25*'$row[2]'+4.92*'$row[3]'+'$row[4]'*300/7+5 where id like '$id';";
}

$conn->query($my_qry);

$q = "select maxcalories from consultant where id like '$id'";
$res = mysqli_query($conn ,$q);
$r = mysqli_fetch_array($res,MYSQLI_NUM);

if($conn->query($mysql_qry) === TRUE) {
	echo "ConsultantUpdateSuccess $r[0]";
}
else 
{
	echo "ConsultantUpdateFail";
}
$conn->close();
 
?>