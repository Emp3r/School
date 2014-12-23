<html>
<head>
	<title>Auta - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".id_return a").click(function(){
			var i = $(this).parent().parent().parent().find('td:nth-child(1)').html();
			var s = $(this).parent().parent().parent().find('td:nth-child(2)').html();
			var z = $(this).parent().parent().parent().find('td:nth-child(3)').html();
			var m = $(this).parent().parent().parent().find('td:nth-child(4)').html();
			
			$('#auto_update_id').val(i);
			$('#auto_update_spz').val(s);
			$('#auto_update_znacka').val(z);
			$('#auto_update_model').val(m);
		});
	});
	</script>
	
</head>
<body>
	<div id="main-div">
	
		<div id="menu">
			<ul id="simple-dropdown-nav">
				<li style="min-width:170px;"><a href="index.php">Formuláře</a>
					<ul>
						<li><a href="f1.php">Zákazníci podle počtu přijatých zásilek</a></li>
						<li><a href="f2.php">Auta a jejich ujeté kilometry</a></li>
						<li><a href="f3.php">Nedodané zásilky a volné prostředky</a></li>
						<li><a href="f4.php">Řidiči a auta podle najetých kilometrů</a></li>
						<li><a href="f5.php">Neuskutečněné a odmítnuté dodávky</a></li>
					</ul>
				</li>
				<li><a href="zakaznici.php">Zákazníci</a></li>
				<li><a href="ridici.php">Řidiči</a></li>
				<li><a href="auta.php" class="selected-tab">Auta</a></li>
				<li><a href="zakazky.php">Zakázky</a></li>
				<li style="min-width:150px;"><a href="dodavky.php">Dodávky</a></li>
			</ul>
		</div>
	
	
		<div id="content">
			
			<!-- TABULKA AUT -->
			<h3>Tabulka všech aut</h3>
			<table>
				<tr>
					<th style="width:120px;">id_auta</th>
					<th>spz</th>
					<th>znacka</th>
					<th>model</th>
					<th style="width:70px;">edit</th>
				</tr>
				<?php
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					mysql_query("SET NAMES utf8");
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}
					$sql = 'SELECT * FROM auto ORDER BY id_auta';
					
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
		
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_auta']}</td>".
							"<td>{$row['spz']}</td><td>{$row['znacka']}</td>".
							"<td>{$row['model']}</td><td style=\"text-align:center;\">".
							"<div class=\"id_return\"><a>upravit</a></div></td></tr>";
					
					} 
					mysql_close($conn);
				?>
			</table>
			<br><br><br>
			
			
			<h3>Nastavení</h3>
			<!-- PŘIDAT -->
			<?php
				if(isset($_POST['add'])) {
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}

					$auto_spz = $_POST['auto_spz'];
					$auto_znacka = $_POST['auto_znacka'];
					$auto_model = $_POST['auto_model'];

					$sql = "INSERT INTO auto (spz, znacka, model) ".
						   "VALUES ('$auto_spz', '$auto_znacka', '$auto_model')";
					
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not enter data: ' . mysql_error());
					}
					echo "Entered data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/auta.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Přidat nové auto</th></tr>
				<tr>
					<td style="width:120px;">SPZ</td>
					<td><input name="auto_spz" type="text" id="auto_spz" size="30"></td>
				</tr>
				<tr>
					<td>Značka</td>
					<td><input name="auto_znacka" type="text" id="auto_znacka" size="30"></td>
				</tr>
				<tr>
					<td>Model</td>
					<td><input name="auto_model" type="text" id="auto_model" size="30"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;">
					<input name="add" type="submit" id="add" value="Přidat">
				</td>
				</tr>
			</table>
			</form>
			<?php } ?><br>
			
			
			<!-- UPRAVIT -->
			<?php
				if(isset($_POST['edit'])) {
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}

					$auto_update_id = $_POST['auto_update_id'];
					$auto_update_spz = $_POST['auto_update_spz'];
					$auto_update_znacka = $_POST['auto_update_znacka'];
					$auto_update_model = $_POST['auto_update_model'];

					$sql = "UPDATE auto SET spz = '$auto_update_spz', 
											znacka = '$auto_update_znacka', 
											model = '$auto_update_model' ".
						   "WHERE $auto_update_id = id_auta;";
						   
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not edit data: ' . mysql_error());
					}
					echo "Edited data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/auta.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Upravit auto</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="auto_update_id" type="text" id="auto_update_id" size="30"></td>
				</tr>
				<tr>
					<td>SPZ</td>
					<td><input name="auto_update_spz" type="text" id="auto_update_spz" size="30"></td>
				</tr>
				<tr>
					<td>Značka</td>
					<td><input name="auto_update_znacka" type="text" id="auto_update_znacka" size="30"></td>
				</tr>
				<tr>
					<td>Model</td>
					<td><input name="auto_update_model" type="text" id="auto_update_model" size="30"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;">
					<input name="edit" type="submit" id="edit" value="Upravit">
				</td>
				</tr>
			</table>
			</form>
			<?php } ?><br>
			
			
			<!-- ODEBRAT -->
			<?php
				if(isset($_POST['delete'])) {
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}

					$auto_id = $_POST['auto_id'];
			
					$sql = "DELETE FROM auto WHERE id_auta = '$auto_id'";
					
					mysql_select_db('dovydata');

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not delete data: ' . mysql_error());
					}
					echo "Deleted data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/auta.php");
					die();
				}
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Smazat auto</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="auto_id" type="text" id="auto_id" size="30"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;">
					<input name="delete" type="submit" id="delete" value="Smazat">
				</td>
				</tr>
			</table>
			</form>
			<?php } ?>
			
			
			
		</div>
		
	</div>
	
</body>
</html>