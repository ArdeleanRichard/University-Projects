<?php 
require "ccconn.php";
$name = $_POST["name"];
$email = $_POST["email"];
$username = $_POST["username"];
$password = $_POST["password"];
$gender = $_POST["gender"];
$weight = $_POST["weight"];
$height = $_POST["height"];
$age = $_POST["age"];
$exercise = $_POST["exercise"];
$daycalories = "0";
$maxcalories = "2000";
$goal = "maintain";

$uald = "select * from user where username like '$username';";
$ures = mysqli_query($conn ,$uald);
$ur = mysqli_fetch_array($ures,MYSQLI_NUM);

if(mysqli_num_rows($ures) > 0) {
	echo "UsernameAlreadyExists";
}
else
{
    $cald = "select * from consultant where username like '$username';";
    $cres = mysqli_query($conn ,$cald);
    $cr = mysqli_fetch_array($cres,MYSQLI_NUM);
    
    if(mysqli_num_rows($cres) > 0) {
    	echo "UsernameAlreadyExists";
    }
    else
    {
        $mysql_qry = "insert into user (name, email, username, password, gender, weight, height, age, exercise, daycalories,maxcalories, goal) values ('$name', '$email', '$username', '$password', '$gender', '$weight', '$height','$age','$exercise','$daycalories','$maxcalories', '$goal')";

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
    }
}


$conn->close();

?>