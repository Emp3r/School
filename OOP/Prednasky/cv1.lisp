;; konstruktivni varianta select-sortu
(defun select-sort-con (list)
  (labels ((remove-first (list el) ; vraci seznam bez prvniho vyskytu daneho prvku, jde nahradit volanim remove s :count 1
             (if (eql (car list) el)
                 (cdr list)
               (cons (car list) (remove-first (cdr list) el)))))
    (if (null list) '()
      (let ((min (apply #'min list))) ; toto by rozhodne melo fungovat, jen pozor na volani s prazdnym seznamem
        (cons min (select-sort-con (remove-first list min)))))))

;; priklad pouziti
(select-sort-con '(2 0 1 4 3 6 1 2)) 


;; destruktivni varianta select-sortu
(defun select-sort-des (whole-list)
  (labels ((min-index (list min i n) ; vraci index prvniho vyskytu minimalniho prvku v seznamu
             (if (null list) i
               (if (> min (car list))
                   (min-index (cdr list) (car list) n (+ n 1))
                 (min-index (cdr list) min i (+ n 1)))))
           (sort-item (list) ; destruktivni zacleneni nejmensiho prvku na prvni misto
             (if (null list) whole-list
               (let ((min (min-index (cdr list) (car list) 0 1)) h)
                 (setf h (nth min list))
                 (setf (nth min list) (car list))
                 (setf (car list) h)
                 (sort-item (cdr list))))))
    (sort-item whole-list)))

;; priklad pouziti
(select-sort-des '(2 0 1 4 3 6 1 2))


;; varianty s predani funkce pro test usporadani 
(defun select-sort-con (list order)
  (labels ((remove-first (list el) 
             (if (eql (car list) el)
                 (cdr list)
               (cons (car list) (remove-first (cdr list) el))))
           (gen-min (list min) ; zobecneni funkce pro vypocet minima
             (if (null list) min
               (if (funcall order (car list) min)
                   (gen-min (cdr list) (car list))
                 (gen-min (cdr list) min)))))
    (if (null list) '()
      (let ((min (gen-min (cdr list) (car list)))) 
        (cons min (select-sort-con (remove-first list min) order))))))

;; priklad pouziti
(select-sort-con '(2 0 1 4 3 6 1 2) #'>) 

(defun select-sort-des (whole-list order)
  (labels ((min-index (list min i n) 
             (if (null list) i
               (if (funcall order (car list) min)
                   (min-index (cdr list) (car list) n (+ n 1))
                 (min-index (cdr list) min i (+ n 1)))))
           (sort-item (list) ; destruktivni zacleneni nejmensiho prvku na prvni misto
             (if (null list) whole-list
               (let ((min (min-index (cdr list) (car list) 0 1)) h)
                 (setf h (nth min list))
                 (setf (nth min list) (car list))
                 (setf (car list) h)
                 (sort-item (cdr list))))))
    (sort-item whole-list)))

;; priklad pouziti
(select-sort-des '(2 0 1 4 3 6 1 2) #'>)