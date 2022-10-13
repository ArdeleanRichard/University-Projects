<?php 
require "ccconn.php";

$id=$_POST["id"];
$mysql_qry = "select * from consultant where id='$id'";

$result = mysqli_query($conn, $mysql_qry);


if($conn->query($mysql_qry) === FALSE) {
echo "Error";
}
else
{
while ($row = mysqli_fetch_array($result)) {
	echo "$row[1] $row[2] $row[3] $row[4] $row[5] $row[6] $row[7] $row[8] $row[9]";
}
}

$conn->close();
 
?>