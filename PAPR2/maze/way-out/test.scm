(load "way-out.scm")

(define test-maze
  (lambda (filename)
     
    (load filename)
    
    (let* ((maze #f)
	   (boy-pos #f) (boy-x #f) (boy-y #f)
	   (trs-pos #f) (trs-x #f) (trs-y #f))

      (define set-maze
	(lambda ()
	  (set! boy-pos (car setting))
	  (set! boy-x (car boy-pos))
	  (set! boy-y (cdr boy-pos))
	  (set! trs-pos (cadr setting))
	  (set! trs-x (car trs-pos))
	  (set! trs-y (cdr trs-pos))
	  (set! maze (caddr setting))))

     
      (define run-loop
	(lambda (i)
	  (let* ((directions (get-directions maze boy-x boy-y))
		 (treasure (get-inc-directions (- trs-x boy-x)
					       (- trs-y boy-y)))
		 (move (eval `(navigator ',directions ',treasure))))
	    (if (and (not move))
		(begin
		  (display "Halt: ")
		  (display i)
		  (newline)
                  i)
		(begin
		  (set! boy-pos (make-move maze boy-pos move))
		  (if (not boy-pos)
		      (begin
			(display "Suicide: ")
			(display i)
			(newline)
                        i)
		      (begin
			
			(set! boy-x (car boy-pos))
			(set! boy-y (cdr boy-pos))
			
                        ;; vypis tahu
                        (if (= (modulo i 50) 0) 
                            (begin
                              (display i)
                              (display " ")
                              ))
			
			(if (equal? boy-pos trs-pos)
			    (begin
			      (display "Moves: ")
			      (display i)
			      (newline)
                              i)
			    (run-loop (+ i 1))))))))))
      
      (set-maze)
      (run-loop 0))))




(define start-time 0)

(set! start-time (current-seconds))

(apply +
       (build-list 1000 (lambda (i)
                          (load "solution.scm")
                          (test-maze 
                           (string-append "maze"
                                          (number->string i)
                                          ".scm")))))

(newline)
(display "time in seconds: ")
(display (- (current-seconds) start-time))




