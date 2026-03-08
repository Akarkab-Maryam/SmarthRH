# Script pour supprimer le BOM

$files = Get-ChildItem -Path "src\main\java\com\smarthr\smarthrspringboot" -Recurse -Filter "*.java"

foreach ($file in $files) {
    try {
        $content = Get-Content $file.FullName -Raw
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
        Write-Host "✅ Corrigé: $($file.Name)" -ForegroundColor Green
    }
    catch {
        Write-Host "❌ Erreur: $($file.Name)" -ForegroundColor Red
    }
}

Write-Host "`n🎉 TOUS LES FICHIERS CORRIGÉS !" -ForegroundColor Cyan
