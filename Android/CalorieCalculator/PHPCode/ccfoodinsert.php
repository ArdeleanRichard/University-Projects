<?php 
require "ccconn.php";
$name = $_POST["name"];
$calories = $_POST["calories"];

$fald = "select * from food where name like '$name';";
$fres = mysqli_query($conn ,$fald);
$fr = mysqli_fetch_array($fres,MYSQLI_NUM);

if(mysqli_num_rows($fres) > 0) {
	echo "FoodAlreadyExists";
}
else
{
$mysql_qry = "insert into food (name, calories) values ('$name', '$calories')";


if($conn->query($mysql_qry) === TRUE) {
echo "InsertFoodSuccess";
}
else {
echo "Error: ".$mysql_qry."<br>".$conn->error;
}
}
$conn->close();
 
?>