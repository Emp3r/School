<html>
<head>
	<title>Zakázky - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".id_return a").click(function(){
			var i = $(this).parent().parent().parent().find('td:nth-child(1)').html();
			var c = $(this).parent().parent().parent().find('td:nth-child(2)').html();
			var s = $(this).parent().parent().parent().find('td:nth-child(5)').html();
			
			$('#zakazka_update_id').val(i);
			$('#zakazka_update_cena').val(c);
			
			$('input:radio[name=zakazka_update_status]').each(function( index, nova ) {
  				if ($(nova).val() == s)
  					$(nova).attr('checked', true);
  			});
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
				<li><a href="auta.php">Auta</a></li>
				<li><a href="zakazky.php" class="selected-tab">Zakázky</a></li>
				<li style="min-width:150px;"><a href="dodavky.php">Dodávky</a></li>
			</ul>
		</div>
	
	
		<div id="content">
			
			
			<!-- TABULKA ZAKÁZEK -->
			<h3>Tabulka všech zakázek</h3>
			<table>
				<tr>
					<th style="width:120px;">id_zakazky</th>
					<th>cena</th>
					<th>jmeno</th>
					<th>adresa</th>
					<th>status</th>
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
					$sql = 'SELECT * FROM zakazka NATURAL JOIN zakaznik ORDER BY id_zakazky';
					
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
		
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_zakazky']}</td>".
							"<td>{$row['cena']}</td><td>{$row['jmeno']}</td>".
							"<td>{$row['adresa']}</td><td>{$row['status']}</td>".
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

					$zakazka_zakaznik = $_POST['zakazka_zakaznik'];
					$zakazka_cena = $_POST['zakazka_cena'];

					$sql = "INSERT INTO zakazka (id_zakaznika, cena, status) ".
						   "VALUES ('$zakazka_zakaznik', '$zakazka_cena', 'nevyřízeno')";
					
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not enter data: ' . mysql_error());
					}
					echo "Entered data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/zakazky.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Přidat novou zakázku</th></tr>
				<tr>
					<td style="width:120px;">ID zákazníka</td>
					<td><input name="zakazka_zakaznik" type="text" id="zakazka_zakaznik" size="30"></td>
				</tr>
				<tr>
					<td>Cena</td>
					<td><input name="zakazka_cena" type="text" id="zakazka_cena" size="30"></td>
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

					$zakazka_update_id = $_POST['zakazka_update_id'];
					$zakazka_update_cena = $_POST['zakazka_update_cena'];
					$zakazka_update_status = $_POST['zakazka_update_status'];
					
					$sql = "UPDATE zakazka SET cena = '$zakazka_update_cena', 
											status = '$zakazka_update_status'".
						   "WHERE $zakazka_update_id = id_zakazky;";
						   
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not edit data: ' . mysql_error());
					}
					echo "Edited data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/zakazky.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Upravit zakázku</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="zakazka_update_id" type="text" id="zakazka_update_id" size="30"></td>
				</tr>
				<tr>
					<td>Cena</td>
					<td><input name="zakazka_update_cena" type="text" id="zakazka_update_cena" size="30"></td>
				</tr>
				<tr>
					<td>Status</td>
					<td>
						<label><input type="radio" name="zakazka_update_status" value="vyřízeno"> vyřízeno</label><br>
						<label><input type="radio" name="zakazka_update_status" value="nevyřízeno"> nevyřízeno</label>
					</td>
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

					$zakazka_id = $_POST['zakazka_id'];
			
					$sql = "DELETE FROM zakazka WHERE id_zakazky = '$zakazka_id'";
					mysql_select_db('dovydata');
			
					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not enter data: ' . mysql_error());
					}
					echo "Entered data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/zakazky.php");
					die();
				}
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Smazat zakázku</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="zakazka_id" type="text" id="zakazka_id"></td>
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