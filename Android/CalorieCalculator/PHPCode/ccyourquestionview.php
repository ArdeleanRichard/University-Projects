<?php 
require "ccconn.php";

$userid=$_POST["userid"];
$mysql_qry = "select question,answer from message where userid='$userid';";

$result = mysqli_query($conn, $mysql_qry);


if($conn->query($mysql_qry) === FALSE) {
echo "Error";
}
else
{
while ($row = mysqli_fetch_array($result)) {
	echo $row[0];
	echo "-";
	if($row[1] === NULL)
		echo "No answer yet";
	else
		echo $row[1];
	echo "-";
}
echo "Gata";
echo "-";
}

$conn->close();
 
?>               