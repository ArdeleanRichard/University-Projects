<?php 
require "ccconn.php";

$id=13;

$mysql_qry = "DELETE FROM user WHERE id like '$id';";


if($conn->query($mysql_qry) === TRUE) {
echo "DeleteSuccess";
}
else {
echo "DeleteFail";
}
$conn->close();
 
?>