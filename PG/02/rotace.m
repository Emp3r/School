function rotace(In)
    % bere na vstup nacteny obrazek, tedy matici
    
    In = rgb2gray(In);
    S = size(In);
   
    for i = 1:S(2)
        for j = 1:S(1)
            Out(i, S(1)-j+1) = In(j, i);
        end
    end
    
    imshow(Out);

end

