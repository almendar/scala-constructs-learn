::#!
@echo off
call scala -savecompiled %0 %*
goto :eof
::!#
Console.println("Hello, world!")
argv.toList foreach Console.println