function ctverce(k, nazev)

    image = ones(2 ^ k , 2 ^ k);
    size = (2 ^ k) / 2;
    point = 1;
    
    while (size >= 1)
        image(point:(point + size - 1) , point:(point + size - 1)) = 0;
        
        point = point + size;
        size = size / 2;
        
    end
    
    imwrite(image, nazev);
    imshow(image);

end

