<html>
<head>
	<title>Neuskutečněné a odmítnuté dodávky - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
		
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#datum_od").datepicker({
			dateFormat: "dd/mm/yy"
		});
		$("#datum_do").datepicker({
			dateFormat: "dd/mm/yy"
		});
	});
	</script>
	
</head>
<body>
	<div id="main-div">
	
		<div id="menu">
			<ul id="simple-dropdown-nav">
				<li style="min-width:170px;"><a href="index.php" class="selected-tab">Formuláře</a>
					<ul>
						<li><a href="f1.php">Zákazníci podle počtu přijatých zásilek</a></li>
						<li><a href="f2.php">Auta a jejich ujeté kilometry</a></li>
						<li><a href="f3.php">Nedodané zásilky a volné prostředky</a></li>
						<li><a href="f4.php">Řidiči a auta podle najetých kilometrů</a></li>
						<li><a href="f5.php" class="selected-tab">Neuskutečněné a odmítnuté dodávky</a></li>
					</ul>
				</li>
				<li><a href="zakaznici.php">Zákazníci</a></li>
				<li><a href="ridici.php">Řidiči</a></li>
				<li><a href="auta.php">Auta</a></li>
				<li><a href="zakazky.php">Zakázky</a></li>
				<li style="min-width:150px;"><a href="dodavky.php">Dodávky</a></li>
			</ul>
		</div>
	
		<div id="content">
			<h3>Neuskutečněné a odmítnuté dodávky</h3>
			
			<div id="zadani">
				<ul><li>přehled odmítnutých dodávek (adresát zásilku nepřijal) a neuskutečněných dodávek (adresát nebyl zastižen) pro zvolený časový interval (od data po datum)</li></ul>
			</div>
			
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Zvolte časový interval</th></tr>
				<tr>
					<td style="width:120px;">Od</td>
					<td><input name="datum_od" type="text" id="datum_od" size="30"></td>
				</tr>
				<tr>
					<td>Do</td>
					<td><input name="datum_do" type="text" id="datum_do" size="30"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;">
						<input name="show" type="submit" id="show" value="Vypsat">
					</td>
				</tr>
			</table>
			</form>
			<br>
			
			<h3><?php echo $_POST['datum_od']." - ".$_POST['datum_do']; ?></h3>
			<table>
				<tr>
					<th style="width:120px;">id_dodavky</th>
					<th>id_zakazky</th>
					<th>adresa</th>
					<th>datum</th>
					<th>status</th>
				</tr>
			<?php		
				if(isset($_POST['show'])) {		
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}

					$datum_od = $_POST['datum_od'];
					$datum_do = $_POST['datum_do'];
					
					list($den, $mesic, $rok) = explode("/", $datum_od);
					$datum_od = $rok."-".$mesic."-".$den;
					list($den, $mesic, $rok) = explode("/", $datum_do);
					$datum_do = $rok."-".$mesic."-".$den;
		
					// TODO
					$sql = "SELECT * FROM dodavka NATURAL JOIN (SELECT adresa, id_zakazky ".
						"FROM zakazka NATURAL JOIN zakaznik) AS adresy WHERE ".
						"datum >= '$datum_od' AND datum <= '$datum_do' AND ".
						"(status = 'nepřijal' OR status = 'nezastižen')";
				
					mysql_query("SET CHARACTER SET utf8");
				
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
	
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_dodavky']}</td><td>{$row['id_zakazky']}</td>".
							"<td>{$row['adresa']}</td><td>{$row['datum']}</td>".
							"<td style=\"text-align:center;color:#d00;\">{$row['status']}</td></tr>";
				
					} 
				
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
				
					mysql_close($conn);
				}				
			?>
			</table>
			<br>
			
		</div>
		
	</div>
</body>
</html>