;; kniha - cena, nazev, autor, pocet stran, rok vydani
;; skladba - nazev, interpret, autori hudby, autori textu, delka (typ cas)
;; video (film) - cena, nazev, rezie, herci, delka, rok, jazyk
;; album - cena, nazev, rok vydani, seznam skladeb, autori, pocet skladeb, celkova delka
;; serial - cena, nazev, rok, seznam dilu, autori, pocet dilu, celkova delka
;; DATABAZE OBCHODU - seznam polozek + pristup, celkova cena vsech veci v obchodu


;; Product ;;
(defclass Product ()
  ((name :initform "")
   (author :initform '())
   (year :initform nil)
   (price :initform nil)))

(defmethod get-name ((p Product)) (slot-value p 'name))

(defmethod set-name ((p Product) name)
  (unless (typep name 'string)
    (error "Maybe new name wasnt string?"))
  (setf (slot-value p 'name) name)
  p)

(defmethod get-author ((p Product)) (copy-list (slot-value p 'author)))

(defmethod set-author ((p Product) author)
  (unless (every (lambda (x)
                   (typep x 'string))
                 author)
    (error "At least one of the author names isnt a string maybe?"))
  (setf (slot-value p 'author) (copy-list author))
  p)

(defmethod get-year ((p Product)) (slot-value p 'year))

(defmethod set-year ((p Product) year)
  (unless (typep year 'integer)
    (error "Year should be integer."))
  (setf (slot-value p 'year) year)
  p)

(defmethod get-price ((p Product)) (slot-value p 'price))

(defmethod set-price ((p Product) price)
  (unless (typep price 'number)
    (error "Price should be number"))
  (setf (slot-value p 'price) price)
  p)


;; MyTime ;;
(defclass MyTime ()
  ((seconds :initform 0)
   (minutes :initform 0)))

(defmethod get-minutes ((ti MyTime)) (slot-value ti 'minutes))

(defmethod set-minutes ((ti MyTime) minutes)
  (unless (typep minutes 'integer)
    (error "Minutes are wrong type."))
  (setf (slot-value ti 'minutes) minutes)
  ti)

(defmethod get-seconds ((ti MyTime)) (slot-value ti 'seconds))

(defmethod set-seconds ((ti MyTime) seconds)
  (unless (typep seconds 'integer)
    (error "Seconds are wrong type."))
  (if (> seconds 59)
      (error "More than 59 seconds? Think about it.")
    (setf (slot-value ti 'seconds) seconds))
  ti)

(defun make-MyTime (min sec)
  (let ((ti (make-instance 'MyTime)))
    (set-seconds ti sec)
    (set-minutes ti min)
    ti))


;; Book ;;
(defclass Book (Product)
  ((pages :initform 0)))

(defmethod get-pages ((b Book)) (slot-value b 'pages))

(defmethod set-pages ((b Book) nb)
  (unless (typep nb 'integer)
    (error "Number of pages is in wrong type."))
  (setf (slot-value b 'pages) nb)
  b)

(defun make-Book (name writer pages year price)
  (let ((bo (make-instance 'Book)))
    (set-name bo name)
    (set-author bo writer)
    (set-year bo year)
    (set-pages bo pages)
    (set-price bo price)
    bo))


;; Track ;;
(defclass Track (Product)
  ((composer :initform "")
   (text :initform "")
   (length :initform (make-instance 'MyTime))))

(defmethod get-name ((tr Track)) (slot-value tr 'name))
(defmethod set-name ((tr Track) na)
  (unless (typep na 'string)
    (error "Name is wrong type. Should be string."))
  (setf (slot-value tr 'name) na)
  tr)

(defmethod get-composer ((tr Track)) (slot-value tr 'composer))
(defmethod set-composer ((tr Track) na)
  (unless (typep na 'string)
    (error "Name is wrong type. Should be string."))
  (setf (slot-value tr 'composer) na)
  tr)

(defmethod get-text ((tr Track)) (slot-value tr 'text))
(defmethod set-text ((tr Track) na)
  (unless (typep na 'string)
    (error "Name is wrong type. Should be string."))
  (setf (slot-value tr 'text) na)
  tr)

(defmethod get-length ((tr Track)) (slot-value tr 'length))
(defmethod set-length ((tr Track) mins secs)
  (let ((tm (slot-value tr 'length)))
    (set-minutes tm mins)
    (set-seconds tm secs)
    tm))

(defun make-Track (name comp tex mins secs)
  (let ((trac (make-instance 'Track)))
    (set-name trac name)
    (set-composer trac comp)
    (set-text trac tex)
    (set-length trac mins secs)
    trac))

(defun make-T (name mins secs)
  (let ((trac (make-instance 'Track)))
    (set-name trac name)
    (set-length trac mins secs)
    trac))


;; Album ;;
(defclass Album (Product)
  ((Tracks :initform '())
   (total-length :initform (make-MyTime 0 0))))

(defmethod get-Tracks ((a Album)) (slot-value a 'Tracks))

(defmethod get-total-length ((a Album)) (slot-value a 'total-length))

(defmethod set-total-length ((a Album) tim)
  (setf (slot-value a 'total-length) tim))

(defmethod set-Tracks ((a Album) Tracks)
  (unless (every (lambda (x)
                   (typep x 'Track))
                 Tracks)
    (error "There should be only tracks in an album."))
  (setf (slot-value a 'Tracks) Tracks)
  a)

(defun make-Album (name author tracks year price)
  (let ((al (make-instance 'Album)))
    (set-name al name)
    (set-author al author)
    (set-Tracks al tracks)
    (let ((m 0) (s 0))
      (dolist (tr tracks)
        (setf m (+ m (get-minutes (get-length tr))))
        (setf s (+ s (get-seconds (get-length tr))))
        (if (> s 59)
            (let ()
              (setf s (- s 60))
              (setf m (+ m 1)))))
      (set-total-length al (make-MyTime m s)))
    (set-year al year)
    (set-price al price)
    al))


;; Movie ;;
(defclass Movie (Product)
  ((actors :initform '())
   (length :initform (make-instance 'MyTime))
   (lang :initform "")))

(defmethod get-actors ((m Movie)) (copy-list (slot-value m 'actors)))

(defmethod set-actors ((m Movie) actors-list)
  (unless (every (lambda (act)
                   (typep act 'string))
                 actors-list)
    (error "Some of the actor names is in wrong type."))
  (setf (slot-value m 'actors) (copy-list actors-list))
  m)

(defmethod get-length ((m Movie)) (slot-value m 'length))

(defmethod set-length ((m Movie) min sec)
  (let ((tim (slot-value m 'length)))
    (set-seconds tim sec)
    (set-minutes tim min)
    tim))

(defmethod get-lang ((m Movie)) (slot-value m 'lang))

(defmethod set-lang ((m Movie) lang)
  (unless (typep lang 'string)
    (error "Language should by type string."))
  (setf (slot-value m 'lang) lang)
  m)

(defun make-Movie (name author actors year min sec lang price)
  (let ((m (make-instance 'Movie)))
    (set-name m name)
    (set-author m author)
    (set-actors m actors)
    (set-year m year)
    (set-length m min sec)
    (set-lang m lang)
    (set-price m price)
    m))


;; Episode ;;
(defclass Episode (Product)
  ((name :initform "")
   (number :initform nil)
   (season :initform nil)
   (length :initform (make-instance 'MyTime))))

(defmethod get-name ((e Episode)) (slot-value e 'name))

(defmethod set-name ((e Episode) nam)
  (unless (typep nam 'string)
    (error "Name of an episode should be type string."))
  (setf (slot-value e 'name) nam)
  e)

(defmethod get-number ((e Episode)) (slot-value e 'number))

(defmethod set-number ((e Episode) numb)
  (unless (typep numb 'integer)
    (error "Number of an episode should be integer."))
  (setf (slot-value e 'number) numb)
  e)

(defmethod get-season ((e Episode)) (slot-value e 'season))

(defmethod set-season ((e Episode) sea)
  (unless (typep sea 'integer)
    (error "Season number should be integer."))
  (setf (slot-value e 'season) sea)
  e)

(defmethod get-length ((e Episode)) (slot-value e 'length))

(defmethod set-length ((e Episode) mins secs)
  (let ((tim (slot-value e 'length)))
    (set-seconds tim secs)
    (set-minutes tim mins)
    tim))

(defun make-Episode (name number season mins secs)
  (let ((e (make-instance 'Episode)))
    (set-name e name)
    (set-number e number)
    (set-season e season)
    (set-length e mins secs)
    e))

;; Series ;;
(defclass Series (Product)
  ((episodes :initform '())
   (total-length :initform (make-MyTime 0 0))))

(defmethod get-episodes ((s Series)) (copy-list (slot-value s 'episodes)))

(defmethod set-episodes ((s Series) episodes-list)
  (unless (every (lambda (e-list)
                   (typep e-list 'Episode))
                   episodes-list)
    (error "Series episodes should be only type episode."))
  (setf (slot-value s 'episodes) (copy-list episodes-list))
  s)

(defmethod get-total-length ((s Series)) (slot-value s 'total-length))

(defmethod set-total-length ((s Series) tim)
  (setf (slot-value s 'total-length) tim))

(defun make-Series (name a episodes-list year price)
  (let ((s (make-instance 'Series)))
    (set-name s name)
    (set-author s a)
    (set-episodes s episodes-list)
    (let ((min 0) (sec 0))
      (dolist (ep episodes-list)
        (setf min (+ min (get-minutes (get-length ep))))
        (setf sec (+ sec (get-seconds (get-length ep))))
        (if (> s 59)
            (let ()
              (setf sec (- sec 60))
              (setf min (+ min 1)))))
      (set-total-length s (make-MyTime min sec)))
    (set-year s year)
    (set-price s price)
    s))

(defmethod add-Episode ((s Series) name number season mins secs)
  (let ((new-Episode (make-Episode name number season mins secs))
        (episode-list (get-episodes s)))
    (setf episode-list (cons new-Episode episode-list))
    (set-episodes s episode-list)
    s))



;; DATABASE ;;
(defclass Database()
  ((books :initform '())
   (Movies :initform '())
   (series :initform '())
   (albums :initform '())
   (price :initform 0)))

(defmethod get-price ((d Database)) (slot-value d 'price))

(defmethod set-price ((d Database) pric)
  (unless (typep pric 'number)
    (error "Price is not type number."))
  (setf (slot-value d 'price) pric)
  d)

;; books
(defmethod get-Books ((d Database)) (copy-list (slot-value d 'books)))

(defmethod set-Books ((d Database) books)
  (unless (every (lambda (x)
                   (typep x 'Book))
                 books)
    (error "You are trying to set book list with at least one non-book type object."))
  (setf (slot-value d 'books) books)
  d)

(defmethod add-Book ((d Database) name writer pages year price)
  (let ((new-Book (make-Book name writer pages year price))
        (books (get-Books d)))
    (setf books (cons new-Book books))
    (set-Books d books)
    (set-price d (+ (get-price d) (get-price new-Book)))
    d))

(defmethod print-Books ((d Database))
  (let ((book-list (get-Books d)))
    (dolist (book book-list)
      (format t "Name: ~s,  Writer: ~s,  Pages: ~s,  Price: ~s~%" (get-name book) (car (get-author book)) (get-pages book) (get-price book))))
  d)

;; Movies
(defmethod get-Movies ((d Database)) (copy-list (slot-value d 'Movies)))

(defmethod set-Movies ((d Database) movies)
  (unless (every (lambda (x)
                   (typep x 'Movie))
                 Movies)
    (error "You are trying to set Movie list with at least one non-Movie type object."))
  (setf (slot-value d 'Movies) movies)
  d)

(defmethod add-Movie ((d Database) name director actors mins secs year lang price)
  (let ((new-Movie (make-Movie name director actors year mins secs lang price))
        (Movies (get-Movies d)))
    (setf Movies (cons new-Movie Movies))
    (set-Movies d Movies)
    (set-price d (+ (get-price d) (get-price new-Movie)))
    d))

(defmethod print-Movies ((d Database))
  (dolist (Movie (get-Movies d))
    (format t "Name: ~s,  Director: ~s,  Actors: ~s, ~s ... Year: ~s,  Length: ~s:~s,  Language: ~s,  Price: ~s~%" (get-name Movie)(car (get-author Movie))(car (get-actors Movie))(second (get-actors Movie))(get-year Movie)(get-minutes (get-length Movie))(get-seconds (get-length Movie))(get-lang Movie)(get-price Movie)))
  d)


;; series
(defmethod get-Series ((d Database))  (slot-value d 'series))

(defmethod set-Series ((d Database) series)
  (unless (every (lambda (x)
                   (typep x 'series))
                 series)
    (error "Series list is wrong. Non-series type appeared."))
  (setf (slot-value d 'series) series)
  d)

(defmethod add-Series ((d Database) name a e year price)
  (let ((new-Series (make-Series name a e year price))
        (serie (get-Series d)))
    (setf serie (cons new-Series serie))
    (set-Series d serie)
    (set-price d (+ (get-price d) (get-price new-Series)))
    d))

(defmethod find-Series ((d Database) name)
  (unless (typep name 'string)
    (error "Searched name is not in type string."))
  (let ((return nil))
    (dolist (series (get-Series d))
      (if (equal name (get-name series))
          (setf return series)))
    return))

(defmethod add-Episode-to-Series ((d Database) ser name n s mins secs)
  (let ((series (find-Series d ser)))
    (if series 
        (add-Episode series name n s mins secs)
      (error "Series doesnt exist in list."))))

(defmethod print-Series ((d Database))
  (dolist (series (get-Series d))
    (format t "Name: ~s,  Author: ~s,  Number of episodes: ~s,  Year: ~s,  Price: ~s~%" (get-name series) (car (get-author series)) (length (get-episodes series)) (get-year series) (get-price series)))
  d)

(defmethod print-Episodes ((d Database) name)
  (let ((series (find-Series d name)))
    (if series
        (dolist (episode (get-episodes series))
          (format t "S:~s E:~s  ~s Length: ~s:~s~%"(get-season episode) (get-number episode) (get-name episode) (get-minutes (get-length episode)) (get-seconds (get-length episode))))
      (error "Series doesnt exist in list."))
    d))

;; album
(defmethod get-Albums ((d Database)) (copy-list (slot-value d 'albums)))

(defmethod set-Albums ((d Database) albumL)
  (unless (every (lambda (x)
                   (typep x 'album))
                 albumL)
    (error "Non-album type appeared in argument."))
  (setf (slot-value d 'albums) (copy-list albumL))
  d)

(defmethod add-Album ((d Database) name interpret tracks year price)
 (let ((ada (make-Album name interpret tracks year price))
       (album-list (get-Albums d)))
     (setf album-list (cons ada album-list))
     (set-Albums d album-list)
     (set-price d (+ (get-price d) (get-price ada)))
     d))

(defmethod print-Albums ((d Database))
  (dolist (album (get-Albums d))
    (format t "Name: ~s,  Author: ~s,  Tracks: ~s,  Total time: ~s:~s,  Year: ~s,  Price: ~s~%" 
            (get-name album)(first (get-author album))(length (get-tracks album)) 
            (get-minutes (get-total-length album)) (get-seconds (get-total-length album)) 
            (get-year album)(get-price album)))
  d)



(defmethod print-all ((d Database))
  (format t "~%~% !!! WELCOME TO OUR SHOP !!! ~%~%")
  (format t "Books:~%")
  (print-Books d)
  (format t "~%")
  (format t "Album:~%")
  (print-Albums d)
  (format t "~%")
  (format t "Movies:~%")
  (print-Movies d)
  (format t "~%")
  (format t "Series:~%")
  (print-Series d)
  (format t "~%")
  d)

(defmethod print-price((d Database))
  (format t "Total price: ~s~%" (get-price d))
  d)

;; TESTS ;;
(defvar d (make-instance 'Database))
(add-Book d "Memento" (list "Radek John") 256 1989 280)
(add-Book d "My deti ze stanice Zoo" (list "Christiane F. ") 304 1998 300)

(add-Album d "Homesick" (list "A Day To Remember") (list (make-T "First" 3 20) (make-T "Second" 4 9) (make-T "Third" 3 30)) 2008 400)
(add-Album d "Meds" (list "Placebo") (list (make-T "Uno" 2 58) (make-T "Dos" 2 33) (make-Track "Tres" "Placebo" "Josh" 2 46)) 2006 420)

(add-Movie d "Requiem for a Dream" (list "Darren Aronofsky") (list " Ellen Burstyn" "Jared Leto") 102 40 2000 "EN" 360)
(add-Movie d "Trainspotting" (list "Danny Boyle") (list " Ewan McGregor" "Ewen Bremner") 94 50 1996 "CZ" 260)

(add-Series d "Breaking Bad" (list "Adam Bernstein" "Jim McKay" "Tricia Brock") '() 2008 720)
(add-Episode-to-Series d "Breaking Bad" "E01" 1 1 50 30)
(add-Episode-to-Series d "Breaking Bad" "E02" 2 1 50 30)


(print-all d) 
(print-price d)

