<?php 
require "ccconn.php";

$id=$_POST["id"];
$mysql_qry = "UPDATE consultant SET daycalories=0 WHERE id='$id';";

$mysql_qry2 = "DELETE FROM day WHERE userid='$id' and admin=0";

$mysql_qry3 = "select maxcalories from consultant where id='$id';";
$result = mysqli_query($conn ,$mysql_qry3);
$row = mysqli_fetch_array($result,MYSQLI_NUM);

if($conn->query($mysql_qry) === TRUE) {
	if($conn->query($mysql_qry2) === TRUE) {
	    if(mysqli_num_rows($result) > 0) {
		    echo "NewDaySuccess $row[0]";
	    }
	}
	else 
	{
		echo "DayFail";
	}
}
else 
{
	echo "DayFail";
}
$conn->close();
 
?>