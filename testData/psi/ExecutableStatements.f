program p
    if(1-2) 10, 20, 30
    assign 10 to a
    backspace 2
    backspace a
    backspace ( 2, iostat=code, err=9 )
    call oops ( text )
end program