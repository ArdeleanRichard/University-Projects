<?php 
require "ccconn.php";
$name = "name";
$email = "email";
$username ="username";
$password = "password";
$gender = "M";
$weight = 55;
$height = 175;
$age = 22;
$exercise = 3;
$daycalories = "0";
$maxcalories = "2000";

$mysql_qry = "insert into user (name, email, username, password, gender, weight, height, age, exercise, daycalories,maxcalories) values ('$name', '$email', '$username', '$password', '$gender', '$weight', '$height','$age','$exercise','$daycalories', '$maxcalories')";

$mysql_qr = "select id,gender,weight,height,age,exercise from user where username like '$username' and password like '$password';";
$result = mysqli_query($conn ,$mysql_qr);
$row = mysqli_fetch_array($result,MYSQLI_NUM);

if(strcmp('$row[1]', "Male") || strcmp('$row[1]', "M"))
{
$my_qry = "UPDATE user SET maxcalories=9.99*'$row[2]'+6.25*'$row[3]'+4.92*'$row[4]'+'$row[5]'*300/7-161 where id like '$row[0]';";
}
else
{
$my_qry = "UPDATE user SET maxcalories=9.99*'$row[2]'+6.25*'$row[3]'+4.92*'$row[4]'+'$row[5]'*300/7+5 where id like '$row[0]';";
}
$conn->query($my_qry);

if($conn->query($mysql_qry) === TRUE) {
echo "InsertSuccess";
}
else {
echo "Error: ".$mysql_qry."<br>".$conn->error;
}
$conn->close();

?>