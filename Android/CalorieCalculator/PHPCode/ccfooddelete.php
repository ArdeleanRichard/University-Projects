<?php 
require "ccconn.php";

$id=$_POST["id"];

$mysql_qry = "DELETE FROM food WHERE id like '$id';";


if($conn->query($mysql_qry) === TRUE) {
echo "DeleteSuccess";
}
else {
echo "DeleteFail";
}
$conn->close();
 
?>