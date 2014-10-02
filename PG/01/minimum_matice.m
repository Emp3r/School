function [ minimum , pocet , indexy ] = minimum_matice( matice )
    minimum = min(min(matice));
    display (minimum);
    
    pocet = sum(sum(matice == minimum));
    display (pocet);
    
    [x, y] = find(matice == minimum);
    indexy = [x, y];
    display (indexy);

end

