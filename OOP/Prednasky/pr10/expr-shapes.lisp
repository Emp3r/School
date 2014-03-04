;; -*- mode: lisp; encoding: utf-8; -*-

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; text-shape
;;;

(defclass text-shape (shape)
  ((text :initform "")
   (position :initform (make-instance 'point))))

(defmethod text ((shape text-shape))
  (slot-value shape 'text))

(defmethod text-position ((shape text-shape))
  (slot-value shape 'position))

(defmethod initialize-instance ((shape text-shape) &key)
  (call-next-method)
  (let ((pos (text-position shape)))
    (set-events pos '(ev-changing ev-change))
    (set-delegate pos shape))
  shape)

(defmethod ev-changing ((shape text-shape) sender msg &rest msg-args)
  (changing shape 'ev-changing sender msg msg-args))

(defmethod ev-change ((shape text-shape) sender msg &rest msg-args)
  (change shape 'ev-change sender msg msg-args))

(defmethod set-text ((shape text-shape) value)
  (with-change (shape 'set-text value)
    (setf (slot-value shape 'text) value)))

(defmethod do-move ((shape text-shape) dx dy)
  (move (text-position shape) dx dy))

(defmethod do-scale ((shape text-shape) coeff center)
  (scale (text-position shape) coeff center))

(defmethod do-rotate ((shape text-shape) angle center)
  (rotate (text-position shape) angle center))

(defmethod left ((shape text-shape))
  (+ (first (mg:get-string-extent (shape-mg-window shape)
                                  (text shape)))
     (x (text-position shape))))

(defmethod top ((shape text-shape))
  (+ (second (mg:get-string-extent (shape-mg-window shape)
                                   (text shape)))
     (y (text-position shape))))

(defmethod right ((shape text-shape))
  (+ (third (mg:get-string-extent (shape-mg-window shape)
                                  (text shape)))
     (x (text-position shape))))

(defmethod bottom ((shape text-shape))
  (+ (fourth (mg:get-string-extent (shape-mg-window shape)
                                   (text shape)))
     (y (text-position shape))))

(defmethod contains-point-p ((shape text-shape) point)
  (and (<= (left shape) (x point) (right shape))
       (<= (top shape) (y point) (bottom shape))))


(defmethod do-draw ((shape text-shape)) 
  (mg:draw-string (shape-mg-window shape)
                  (text shape)
                  (x (text-position shape)) 
                  (y (text-position shape))) 
  shape)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;
;;;; expr-shapes.lisp
;;;;
;;;; Ukázka použití vícenásobné dědičnosti: grafické objekty, které jsou současně vzorci.
;;;; Narozdíl od jednoduššího příkladu ze souboru trees.lisp dědí nově definované třídy
;;;; (const-shape, var-shape, +-expr-shape atd.) z obou stran nejen metody, ale i sloty.
;;;;
;;;; Při použití vícenásobné dědičnosti je třeba vždy zvážit, zda výhody, které poskytuje,
;;;; vyváží případné problémy, které způsobuje. U tohoto příkladu je zcela rozumnou možností
;;;; použít pro definici třídy expr-shape a jejích potomků pouze jednoduchou dědičnost.
;;;; Necháváme na zvážení, jak.
;;;;
;;;; Je třeba načíst knihovnu micro-graphics a soubory 09.lisp a expressions.lisp.
;;;;

(defclass expr-shape (text-shape)
  ())

(defmethod text ((shape expr-shape))
  (format nil "~a" (representation shape)))

(defmethod set-text ((shape expr-shape) value)
  (error "Cannot set text of expr-shape"))

(defclass const-shape (const expr-shape)
  ())

(defclass var-shape (var expr-shape)
  ())

(defclass +-expr-shape (+-expr expr-shape)
  ())

(defclass --expr-shape (--expr expr-shape)
  ())

(defclass *-expr-shape (*-expr expr-shape)
  ())

(defclass /-expr-shape (/-expr expr-shape)
  ())

;; Nastavení globálních proměnných, aby funkce parse vytvářela instance
;; požadovaných tříd (podívejte se na její definici).
(setf *const-expr-class* 'const-shape)
(setf *var-expr-class* 'var-shape)
(setf *+-expr-class* '+-expr-shape)
(setf *--expr-class* '--expr-shape)
(setf **-expr-class* '*-expr-shape)
(setf */-expr-class* '/-expr-shape)

#|
(setf e (parse '(+ 1 (/ x y))))
(setf w (make-instance 'window))
(set-shape w e)
(move e 50 50)
|#