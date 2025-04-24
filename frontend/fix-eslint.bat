@echo off
echo Installation des dépendances nécessaires...
npm install --save-dev rimraf @babel/preset-env
echo.
echo Suppression du cache ESLint...
npx rimraf node_modules/.cache/eslint-loader
npx rimraf .eslintcache
echo.
echo Nettoyage terminé. Exécution de lint:fix...
npm run lint:fix
echo.
echo Vous pouvez maintenant exécuter "npm run serve" pour démarrer le projet. 