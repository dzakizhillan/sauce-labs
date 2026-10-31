param([string]$Keys = '', [string]$Text = '', [int]$X = -1, [int]$Y = -1, [switch]$Double, [switch]$Hover, [switch]$DriverMenu, [int]$WaitMs = 1000)
Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing
$taskShell = New-Object -ComObject WScript.Shell
$taskProcess = Get-Process katalonc -ErrorAction SilentlyContinue | Select-Object -First 1
if ($taskProcess) { $taskShell.AppActivate($taskProcess.Id) | Out-Null; Start-Sleep -Milliseconds 300 }
Add-Type @'
using System;
using System.Runtime.InteropServices;
public class KatalonMouse {
 [DllImport("user32.dll")] public static extern bool SetCursorPos(int x, int y);
 [DllImport("user32.dll")] public static extern void mouse_event(uint flags, uint dx, uint dy, uint data, UIntPtr extra);
}
'@
if ($DriverMenu) {
    [KatalonMouse]::SetCursorPos(400,32) | Out-Null
    [KatalonMouse]::mouse_event(2,0,0,0,[UIntPtr]::Zero)
    [KatalonMouse]::mouse_event(4,0,0,0,[UIntPtr]::Zero)
    Start-Sleep -Milliseconds 500
    $taskShell.SendKeys('{DOWN}')
    Start-Sleep -Milliseconds 200
    $taskShell.SendKeys('{DOWN}')
    Start-Sleep -Milliseconds 200
    $taskShell.SendKeys('{RIGHT}')
    Start-Sleep -Milliseconds 1000
}
if ($X -ge 0 -and $Y -ge 0) {
    [KatalonMouse]::SetCursorPos($X,$Y) | Out-Null
    if (-not $Hover) {
    [KatalonMouse]::mouse_event(2,0,0,0,[UIntPtr]::Zero)
    [KatalonMouse]::mouse_event(4,0,0,0,[UIntPtr]::Zero)
    if ($Double) {
        Start-Sleep -Milliseconds 70
        [KatalonMouse]::mouse_event(2,0,0,0,[UIntPtr]::Zero)
        [KatalonMouse]::mouse_event(4,0,0,0,[UIntPtr]::Zero)
    }
    }
}
if ($Text) {
    Set-Clipboard -Value $Text
    (New-Object -ComObject WScript.Shell).SendKeys('^a^v')
    Start-Sleep -Milliseconds 300
}
if ($Keys) { (New-Object -ComObject WScript.Shell).SendKeys($Keys) }
Start-Sleep -Milliseconds $WaitMs
$bounds = [System.Windows.Forms.SystemInformation]::VirtualScreen
$shot = New-Object System.Drawing.Bitmap($bounds.Width,$bounds.Height)
$graphics = [System.Drawing.Graphics]::FromImage($shot)
$graphics.CopyFromScreen($bounds.Left,$bounds.Top,0,0,$shot.Size)
$shot.Save((Join-Path $PSScriptRoot '../docs/katalon-screen.png'))
$graphics.Dispose()
$shot.Dispose()
