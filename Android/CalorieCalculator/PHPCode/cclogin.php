<?php 
require "ccconn.php";
$user_name = $_POST["username"];
$user_pass = $_POST["password"];
$date = $_POST["date"];
$mysql_qry = "select id,gender,weight,height,age,exercise,goal from user where username like '$user_name' and password like '$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);
$row = mysqli_fetch_array($result,MYSQLI_NUM);


$date_qry = "SELECT date FROM user WHERE id='$row[0]';";
$olddate = mysqli_query($conn ,$date_qry);
$olddater= mysqli_fetch_array($olddate,MYSQLI_NUM);

if($olddater[0] != $date)
{
	$newdate_qry = "UPDATE user SET date='$date', daycalories=0 WHERE id='$row[0]';";
	$newdate_res = mysqli_query($conn, $newdate_qry);
	$delhis_qry = "DELETE FROM day WHERE userid='$row[0]' and admin=0";
	$delhis_res = mysqli_query($conn, $delhis_qry);
}


$qry = "select daycalories,maxcalories from user where id='$row[0]';";
$res = mysqli_query($conn ,$qry);
$r = mysqli_fetch_array($res,MYSQLI_NUM);


if(mysqli_num_rows($result) > 0) {
	
	echo "UserLoginSuccess $row[0] $row[1] $row[2] $row[3] $row[4] $row[5] $r[0] $r[1] $row[6]";
}
else {
	$mysql_qry = "select id,gender,weight,height,age,exercise,goal from consultant where username like '$user_name' and password like '$user_pass';";
	$result = mysqli_query($conn ,$mysql_qry);
	$row = mysqli_fetch_array($result,MYSQLI_NUM);
	

	$date_qry = "SELECT date FROM consultant WHERE id='$row[0]';";
	$olddate = mysqli_query($conn, $date_qry);
	$olddater= mysqli_fetch_array($olddate,MYSQLI_NUM);

	if($olddater[0] != $date)
	{
		$newdate_qry = "UPDATE consultant SET date='$date', daycalories=0 WHERE id='$row[0]';";
		$newdate_res = mysqli_query($conn, $newdate_qry);
		$delhis_qry = "DELETE FROM day WHERE userid='$row[0]' and admin=1";
		$delhis_res = mysqli_query($conn, $delhis_qry);
	}


	$qry = "select daycalories,maxcalories from consultant where id like '$row[0]';";
	$res = mysqli_query($conn ,$qry);
	$r = mysqli_fetch_array($res,MYSQLI_NUM);

	if(mysqli_num_rows($result) > 0) {
		echo "ConsultantLoginSuccess $row[0] $row[1] $row[2] $row[3] $row[4] $row[5] $r[0] $r[1] $row[6]";
	}
	else
	{
		echo "LoginFail";
	}
}

?>