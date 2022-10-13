<?php 
require "ccconn.php";

$mysql_qry = "select id,question from message where answer IS null";

$result = mysqli_query($conn, $mysql_qry);


if($conn->query($mysql_qry) === FALSE) {
echo "Error";
}
else
{
while ($row = mysqli_fetch_array($result)) {
	echo $row[0];
	echo "-";
	echo $row[1];
	echo "-";
}
echo 'Gata';

}

$conn->close();
 
?>       