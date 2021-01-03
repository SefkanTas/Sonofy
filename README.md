# Sonofy

[![Build Status](https://travis-ci.com/SefkanTas/Sonofy.svg?branch=master)](https://travis-ci.com/SefkanTas/Sonofy) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=SefkanTas_Sonofy&metric=alert_status)](https://sonarcloud.io/dashboard?id=SefkanTas_Sonofy) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=SefkanTas_Sonofy&metric=code_smells)](https://sonarcloud.io/dashboard?id=SefkanTas_Sonofy) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=SefkanTas_Sonofy&metric=bugs)](https://sonarcloud.io/dashboard?id=SefkanTas_Sonofy) [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=SefkanTas_Sonofy&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=SefkanTas_Sonofy)  [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=SefkanTas_Sonofy&metric=coverage)](https://sonarcloud.io/dashboard?id=SefkanTas_Sonofy)

Sonofy offre de la reconnaissance aux nouveaux artistes qui ont du talent et n’arrivent pas à obtenir la visibilité souhaitée.

Les abonnés qui sont passionnés et inspirés par la musique ont la possibilité d’aider les artistes à se faire connaître en partageant leurs musiques permettant ainsi de découvrir et de diversifier les styles musicaux.

## Télécharger l'application sur un appareil Android
---

Vous pouvez télécharger Sonofy directement sur votre smartphone Android en cliquant [ici](https://github.com/SefkanTas/Sonofy/releases/download/v1.0-beta.1/app-release.apk).

## Installation sur ordinateur pour devéloppeur
Vous devez premièrement installer [**Android Studio**](https://developer.android.com/studio) correspondant à votre système d'exploitation (Windows, Linux, Mac OS).


### Pour Windows
---
1 . Si vous avez télécharger le fichier avec l'extension ```.exe``` , qui est recommandé, double-cliquez dessus pour le lancer.
    Si vous avez télécharger le fichier avec l'extension ```.zip```, décompresser le puis copier le dossier android-studio dans le dossier Program Files. Ensuite 
    ouvrez            
    ```android-studio > bin``` puis lancer l'executable ```studio64.exe``` (pour les machines de 64 bits) ou ```studio.exe``` (pour les machines de 32 bits).

2 . Suivez les instructions du panneau d'installation d'Android Studio et installer les paquets SDK qui vous sont recommandés. 


### Pour MAC OS
---
1 . Lancer le fichier Android Studio DMG.

2 . Faites glisser le fichier dans le dossier ```Applications``` puis lancer le fichier ```Android Studio```.

3 . Sélectionner les importations que vous souhaitez installer puis cliquer sur ```OK```.

4 . Le panneau d'installation d'Android Studio vous guidera pour la suite de l'installation ainsi que les paquets SDK nécessaires à son bon fonctionnement. 



### Pour Linux
---
1 . Décompresser le fichier ```.zip``` , puis déplacer le dans ```/usr/local/``` (recommandé pour une utilisation personnelle) ou dans ```/opt/``` pour les autres 
    utilisateurs.

2 . Avant de lancer l'installation, il faudra installer certains paquets avec la commande suivante : 

 * Pour les machines de 64 bits utilisant Ubuntu :
 
```
sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 libbz2-1.0:i386

```

 * Pour les machines de 64 bits utilisant Fedora :

```
sudo yum install zlib.i686 ncurses-libs.i686 bzip2-libs.i686

```

3 . Ensuite accéder au fichier ```android-studio/bin/``` puis exécuter le fichier ```studio.sh```.

4 . Sélectionner si vous souhaitez importer les paramètres Android Studio précédents ou non, puis cliquez sur ```OK```.

5 . Puis le panneau d'installation d'Android Studio vous guidera pour terminer l'installation.

* Si vous rencontrez des difficultés avec l'installation (une erreur) :
   - En lancant l'application : Il faut supprimer le fichier ```Google``` qui se trouve dans le fichier cacher ```.local/``` puis fermer votre projet et le ré-ouvrir.
   - En voulant exécuter votre projet :  
                                          * Une erreur concernant l'émulateur : Il vous faudra installer l'émulateur que vous préférez avec le ```AVD Manager``` 
                                           que vous trouverez en faisant ```ctrl + n``` puis en le cherchant. Il vous proposera plusieurs choix d'écrans puis 
                                           télécharger ceux qui vous plairont. Ensuite fermer le projet et ré-ouvrer le et vous pourrez ensuite l'exécuter avec 
                                           l'émulateur de votre choix.</br>
                                          * Une erreur concernant le SDK : Fermer votre projet ```File > Close project```, et relancer Android Studio. Puis la 
                                           fenêtre d'accueil vous signalera qu'il manque le SDK et vous proposera de l'installer par la suite.



</br>
</br>
</br>

Pour lancer l'application depuis Android Studio vous avez le choix entre :
- [utiliser un appareil android](https://developer.android.com/studio/run/device)
- [utiliser un émulateur android](https://developer.android.com/studio/run/emulator)
