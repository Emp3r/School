;; Time
(defclass time()
  ((minutes :initform 0)
   (seconds :initform 0)))

(defmethod getMinutes ((time time))
  (slot-value time 'minutes))
(defmethod getSeconds ((time time))
  (slot-value time 'seconds))

(defmethod setTime ((time time) x y)
  (if (>= y 60)
      (error "1 minute has 60 seconds")
    (setf (slot-value time 'minutes) x
          (slot-value time 'seconds) y))
  time)

(defun makeTime (x y)
  (setTime (make-instance 'time) x y))


;; Track
(defclass track()
  ((length :initform (make-instance 'time))
   (name :initform "")))

(defmethod getLength ((track track))
  (slot-value track 'length))
(defmethod getName ((track track))
  (slot-value track 'name))

(defmethod setName ((track track) name)
  (unless (typep name 'string)
    (error "Name should be type string"))
  (setf (slot-value track 'name) name))
(defmethod setLength ((track track) x y)
  (setTime (getLength track) x y))

;; print-track
(defmethod print-track ((track track))
  (format t "~%Name:~s" (getName track))
  (format t "Time: ~s:~s" (getMinutes (getLength track)) (getSeconds (getLength track)))))

(defun makeTrack (name x y)
  (let ((tra (make-instance 'track)))
    (setName tra name)
    (setLength tra x y)
    tra))

(defmethod track-time-in-seconds ((track track))
  (+ (* 60 (getMinutes (getLength track))) (getSeconds (getLength track))))


;; Album
(defclass album()
  ((name :initform "")
   (author :initform "")
   (tracks :initform nil)))

(defmethod getAlbumName ((album album))
  (slot-value album 'name))
(defmethod getAlbumAuthor ((album album))
  (slot-value album 'author))
(defmethod getAlbumTracks ((album album))
  (copy-list (slot-value album 'tracks)))

(defmethod setAlbumName ((album album) nm)
  (unless (typep nm 'string)
    (error "Name should be type string"))
  (setf (slot-value album 'name) nm)
  album)
(defmethod setAlbumAuthor ((album album) author)
  (unless (typep author 'string)
    (error "Name should be type string"))
  (setf (slot-value album 'author) author)
  album)
(defmethod setAlbumTracks ((album album) tracklist)
  (unless (every (lambda (pc)
                   (typep pc 'track))
                 tracklist)
    (error "Non-track type in list"))
  (setf (slot-value album 'tracks) (copy-list tracklist))
  album)

(defmethod tracksCount ((album album))
  (length (getAlbumTracks album)))

(defmethod albumTotalTime-seconds ((album album))
  (let ((accum 0)
        (tracklist (getAlbumTracks album)))
    (dolist (track tracklist)
      (setf accum (+ accum (track-time-in-seconds track))))
    accum))



(defmethod totalTime ((album album))
  (let ((inSeconds (albumTotalTime-seconds album)))
    (multiple-value-bind (quot rem)
        (floor inSeconds 60)
      (format t "Total time ~s:~s" quot rem)))
  album)

(defmethod albumPrint ((album album))
  (format t "Name: ~s Author: ~s" (getAlbumName album) (getAlbumAuthor album))
  (format t "~%Number of tracks: ~s~%" (tracksCount album))
  (totalTime album)
  (dolist (track (getAlbumTracks album))
    (print-track track))
  (format t "~%")
  album)

(defmethod addTrack ((album album) name x y)
  (let ((trackList (getAlbumTracks album))
        (newTrack (makeTrack name x y)))
    (setf trackList (cons newTrack trackList))
    (setAlbumTracks album trackList)
    album))

(defun makeAlbum (name author tracklist)
  (let ((a (make-instance 'album)))
    (setAlbumName a name)
    (setAlbumAuthor a author)
    (setAlbumTracks a tracklist)
    a))

