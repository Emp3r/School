<html>
<head>
	<title>Dodávky - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".id_return a").click(function(){
			var i = $(this).parent().parent().parent().find('td:nth-child(1)').html();
			var d = $(this).parent().parent().parent().find('td:nth-child(2)').html();
			var k = $(this).parent().parent().parent().find('td:nth-child(6)').html();
			var s = $(this).parent().parent().parent().find('td:nth-child(7)').html();
			
			$('#dodavka_update_id').val(i);
			$('#dodavka_update_datum').val(d);
			$('#dodavka_update_km').val(k);
			
			$('input:radio[name=dodavka_update_status]').each(function( index, nova ) {
  				if ($(nova).val() == s)
  					$(nova).attr('checked', true);
			});
		});
		
		$("#dodavka_update_datum").datepicker({
			dateFormat: "dd/mm/yy"
		});
		$("#dodavka_datum").datepicker({
			dateFormat: "dd/mm/yy"
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
				<li><a href="zakazky.php">Zakázky</a></li>
				<li style="min-width:150px;"><a href="dodavky.php" class="selected-tab">Dodávky</a></li>
			</ul>
		</div>
	
	
		<div id="content">
			<!-- TABULKA DODÁVEK -->
			<h3>Tabulka všech dodávek</h3>
			<table>
				<tr>
					<th style="width:120px;">id_dodavky</th>
					<th>datum</th>
					<th>id_zakazky</th>
					<th>id_ridice</th>
					<th>id_auta</th>
					<th>km</th>
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
					$sql = 'SELECT * FROM dodavka ORDER BY id_dodavky';
					
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
		
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
						
						
						$time = strtotime($row['datum']);
						$my_format = date("d/m/Y", $time);
					
						echo "<tr><td>{$row['id_dodavky']}</td>".
							"<td>{$my_format}</td><td>{$row['id_zakazky']}</td>".
							"<td>{$row['id_ridice']}</td><td>{$row['id_auta']}</td>".
							"<td>{$row['najeto_km']}</td><td>{$row['status']}</td>".
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

					$dodavka_zakazka = $_POST['dodavka_zakazka'];
					$dodavka_ridic = $_POST['dodavka_ridic'];
					$dodavka_auto = $_POST['dodavka_auto'];
					$dodavka_datum = $_POST['dodavka_datum'];
					
					list($den,$mesic,$rok) = explode("/",$dodavka_datum);
					$formated_date = $rok."-".$mesic."-".$den;

					$sql = "INSERT INTO dodavka (id_zakazky, id_ridice, id_auta, datum, najeto_km, status) ".
						   "VALUES ('$dodavka_zakazka', '$dodavka_ridic', '$dodavka_auto', '$formated_date', '0.00', 'nevyřízeno')";
					
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");

					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not enter data: ' . mysql_error());
					}
					echo "Entered data successfully\n";
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/dodavky.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Přidat novou dodávku</th></tr>
				<tr>
					<td style="width:120px;">ID zakázky</td>
					<td><input name="dodavka_zakazka" type="text" id="dodavka_zakazka" size="30"></td>
				</tr><tr>
					<td>ID řidiče</td>
					<td><input name="dodavka_ridic" type="text" id="dodavka_ridic" size="30"></td>
				</tr><tr>
					<td>ID auta</td>
					<td><input name="dodavka_auto" type="text" id="dodavka_auto" size="30"></td>
				</tr>
				<tr>
					<td>Datum</td>
					<td><input name="dodavka_datum" type="text" id="dodavka_datum" size="30"></td>
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

					$dodavka_update_id = $_POST['dodavka_update_id'];
					$dodavka_update_datum = $_POST['dodavka_update_datum'];
					$dodavka_update_km = $_POST['dodavka_update_km'];
					$dodavka_update_status = $_POST['dodavka_update_status'];
					
					list($den,$mesic,$rok) = explode("/",$dodavka_update_datum);
					$formated_date = $rok."-".$mesic."-".$den;

					$sql = "UPDATE dodavka SET datum = '$formated_date', 
											   najeto_km = '$dodavka_update_km', 
											   status = '$dodavka_update_status'".
						   "WHERE $dodavka_update_id = id_dodavky;";
						   
					mysql_select_db('dovydata');
					mysql_query("SET CHARACTER SET utf8");
					$retval = mysql_query( $sql, $conn );
					
					if( !$retval ) {
						die('Could not edit data: ' . mysql_error());
					}
					echo "Edited data successfully\n";
					
					$sql = "SELECT * FROM dodavka WHERE $dodavka_update_id = id_dodavky;";
					$retval = mysql_query( $sql, $conn );
					$row = mysql_fetch_array($retval, MYSQL_ASSOC);
					$the_id = $row['id_zakazky'];
						
					if ($dodavka_update_status == "vyřízeno") {
						$sql = "UPDATE zakazka SET status = '$dodavka_update_status' WHERE id_zakazky = $the_id";
						$retval = mysql_query( $sql, $conn );
					}
					else {
						$sql = "UPDATE zakazka SET status = 'nevyřízeno' WHERE id_zakazky = $the_id";
						$retval = mysql_query( $sql, $conn );
					}
					
					mysql_close($conn);
					header("Location: http://dovy.emper.cz/dodavky.php");
					die();
				} 
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Upravit dodávku</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="dodavka_update_id" type="text" id="dodavka_update_id" size="30"></td>
				</tr>
				<tr>
					<td>Datum</td>
					<td><input name="dodavka_update_datum" type="text" id="dodavka_update_datum" size="30"></td>
				</tr>
				<tr>
					<td>Najeto km</td>
					<td><input name="dodavka_update_km" type="text" id="dodavka_update_km" size="30"></td>
				</tr>
				<tr>
					<td>Status</td>
					<td>
						<label><input type="radio" name="dodavka_update_status" value="vyřízeno"> vyřízeno</label><br>
						<label><input type="radio" name="dodavka_update_status" value="nezastižen"> nezastižen</label><br>
						<label><input type="radio" name="dodavka_update_status" value="nepřijal"> nepřijal</label><br>
						<label><input type="radio" name="dodavka_update_status" value="nevyřízeno"> nevyřízeno</label>
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

					$dodavka_id = $_POST['dodavka_id'];
			
					$sql = "DELETE FROM dodavka WHERE id_dodavky = '$dodavka_id'";
					mysql_select_db('dovydata');
			
					$retval = mysql_query( $sql, $conn );
					if( !$retval ) {
						die('Could not enter data: ' . mysql_error());
					}
					echo "Deleted data successfully\n";
					mysql_close($conn);
					
					header("Location: http://dovy.emper.cz/dodavky.php");
					die();
				}
				else {
			?>
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Smazat dodávku</th></tr>
				<tr>
					<td style="width:120px;">ID</td>
					<td><input name="dodavka_id" type="text" id="dodavka_id"></td>
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