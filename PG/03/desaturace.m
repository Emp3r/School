function output = desaturace(obrazek, s)

    gray = rgb2gray(obrazek);
    output = obrazek;
    
    for i=1:3
        output(:, :, i) = gray;
    end

    output = (1.0 - s) * output + s * obrazek;

    imshow(output);
end