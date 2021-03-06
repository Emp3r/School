<html>
<head>
	<title>Řidiči - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".id_return a").click(function(){
			var i = $(this).parent().parent().parent().find('td:nth-child(1)').html();
			var j = $(this).parent().parent().parent().find('td:nth-child(2)').html();
			var t = $(this).parent().parent().parent().find('td:nth-child(3)').html();
			
			$('#ridic_update_id').val(i);
			$('#ridic_update_name').val(j);
			$('#ridic_update_telefon').val(t);
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
				<li><a href="ridici.php" class="selected-tab">Řidiči</a></li>
				<li><a href="auta.php">Auta</a></li>
				<li><a href="zakazky.php">Zakázky</a></li>
				<li style="min-width:150px;"><a href="dodavky.php">Dodávky</a></li>
			</ul>
		</div>
	
		<div id="content">
			
			<!-- TABULKA ŘIDIČŮ -->
			<h3>Tabulka všech řidičů</h3>
			<table>
				<tr>
					<th style="width:120px;">id_ridice</th>
					<th>jmeno</th>
					<th>telefon</th>
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
					$sql = 'SELECT * FROM ridic ORDER BY id_ridice';
					
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
		
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_ridice']}</td>".
							"<td>{$row['jmeno']}</td><td>{$row['telefon']}</td>".
							"<td style=\"text-align:center;\">".
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

					$ridic_name = $_POST['ridic_name'];
					$ridic_telefon = $_POST['ridic_telefon'];

					$sql = "INSERT INTO ridic (jmeno, telefon) ".
						   "VALUES ('$ridic_name', '$ridic_telefon')";
					
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not enter data: ' . mysql_error());
					}
					echo "Entered data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/ridici.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Přidat nového řidiče</th></tr>
				<tr>
					<td style="width:120px;">Jméno</td>
					<td><input name="ridic_name" type="text" id="ridic_name" size="30"></td>
				</tr>
				<tr>
					<td>Telefon</td>
					<td><input name="ridic_telefon" type="text" id="ridic_telefon" size="30"></td>
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

					$ridic_update_id = $_POST['ridic_update_id'];
					$ridic_update_name = $_POST['ridic_update_name'];
					$ridic_update_telefon = $_POST['ridic_update_telefon'];

					$sql = "UPDATE ridic SET jmeno = '$ridic_update_name', 
											 telefon = '$ridic_update_telefon' ".
						   "WHERE $ridic_update_id = id_ridice;";
						   
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not edit data: ' . mysql_error());
					}
					echo "Edited data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/ridici.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Upravit řidiče</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="ridic_update_id" type="text" id="ridic_update_id" size="30"></td>
				</tr>
				<tr>
					<td>Jméno</td>
					<td><input name="ridic_update_name" type="text" id="ridic_update_name" size="30"></td>
				</tr>
				<tr>
					<td>Telefon</td>
					<td><input name="ridic_update_telefon" type="text" id="ridic_update_telefon" size="30"></td>
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

					$ridic_id = $_POST['ridic_id'];
			
					$sql = "DELETE FROM ridic WHERE id_ridice = '$ridic_id'";
					
					mysql_select_db('dovydata');

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not delete data: ' . mysql_error());
					}
					echo "Deleted data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/ridici.php");
					die();
				}
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Smazat řidiče</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="ridic_id" type="text" id="ridic_id" size="30"></td>
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