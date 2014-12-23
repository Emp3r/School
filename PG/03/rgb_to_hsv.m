function output = rgb_to_hsv(obrazek)

    [rows, columns, ~] = size(obrazek);
    temp = double(obrazek) / 255;
    output = zeros(rows, columns, 3);

    for i = 1:rows
        for j = 1:columns
            r = temp(i, j, 1);
            g = temp(i, j, 2);
            b = temp(i, j, 3);
            maximum = max([r, g, b]); 
            minimum = min([r, g, b]);
            h = 0;
            s = 0;
            
            if (maximum == r) && (g >= b)
                h = 60 * (g - b) / (maximum - minimum) / 360;
            elseif (maximum == r) && (g < b)
                h = (60 * (g - b) / (maximum - minimum) + 360) / 360;
            elseif (maximum == g)
                h = (60 * (b - r) / (maximum - minimum) + 120) / 360;
            elseif (maximum == b)
                h = (60 * (r - g) / (maximum - minimum) + 240) / 360;
            end
            
            if (maximum ~= 0)
                s = 1 - minimum / maximum;
            end
            
            output(i, j, 1) = h;
            output(i, j, 2) = s;
            output(i, j, 3) = maximum;
        end
    end
    
    imshow(output);
end

