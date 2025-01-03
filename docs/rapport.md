---
title: "Laboratoire 8 - Chess"
author: "Cseres Leonard, Aladin Iseni"
date: \today
geometry: margin=1.75in
papersize: a4
header-includes:
  - \usepackage{fancyhdr}
  - \pagestyle{fancy}
  - \fancyhead[L]{Rapport de Projet BDR}
  - \fancyhead[R]{Page \thepage}
  - \fancyfoot[C]{}
  - \fancyfoot[R]{\thepage}
toc: true
lang: fr
numbersections: true
---

\newpage

# Introduction

L'objectif de ce laboratoire est de développer un jeu d'échecs fonctionnel
respectant les règles de base. Le projet inclut les fonctionnalités suivantes:
déplacements des pièces, coups spéciaux (roque, prise en passant, promotion des
pions) et gestion des états de jeu (par exemple, échec). Les objectifs bonus
consistent à implémenter la détection de l'échec et mat ainsi que du pat.

Pour simplifier le développement, les éléments suivants nous ont été fournis:

- **Enums:** `PieceType` pour les types de pièces et `PlayerColor` pour les
  couleurs des joueurs.
- **Interfaces:** `ChessController` et `ChessView` pour la gestion du jeu et de
  l'interface utilisateur.
- **Vues préconstruites:** Une vue graphique (`GUIView`) et une vue en mode
  texte (`ConsoleView`).

L'implémentation se concentre sur un nouveau package `engine` qui encapsule la
logique du jeu tout en exploitant les interfaces fournies pour l’interaction.

---

# Conception et Architecture

Notre approche respecte les principes de conception orientée objet, en
garantissant l'encapsulation, la réutilisabilité et la modularité. Le package
`engine` contient les classes et la logique pour la gestion du jeu, le suivi de
l'état de l'échiquier et la génération des mouvements.

## Composants Clés

- **`ChessEngine`:** Gère le déroulement du jeu et communique avec la vue.
- **`ChessBoard`:** Représente l'échiquier, suit les pièces et valide les états
  du jeu.
- **`ChessBoardView`:** Interface de lecture (view) de l'échiquier, qui ne
  permet pas de le modifier.
- **`ChessPiece`:** Classe abstraite définissant le comportement commun à toutes
  les pièces, étendue par des sous-classes spécifiques (par exemple, `Pawn`,
  `Rook`, etc.).
- **`MoveGenerator`:** Classe abstraite responsable de la génération des
  mouvements possibles pour les pièces.

## Diagramme UML

Le diagramme UML fournit une vue d'ensemble de la structure et des
relations du système. Les élements grisés représentent le code que nous avons
utilisé et non pas implémenté.

![Schéma UML (Vue simplifiée)](./assets/uml-simple.svg){ width=95% }

![Schéma UML (Vue détaillée)](./assets/uml.svg)

\newpage

# Caractéristiques Principales

## Détection de Fin de Jeu

Le système vérifie:

- **Échec et mat:** Lorsque le roi est en échec et qu'aucun mouvement légal
  n'est possible.
- **Pat:** Lorsque aucun mouvement légal n'est possible, mais que le roi n'est
  pas en échec.

## Règles Spéciales

- **Roque:** Vérifie que le roi et la tour concernés n'ont pas bougé, que le
  chemin est libre et que les cases traversées ne sont pas attaquées.
- **Prise en passant:** Implémente la capture d'un pion adjacent qui a avancé de
  deux cases à son premier mouvement.
- **Promotion de pions:** Demande au joueur de choisir un type de promotion
  (tour, cavalier, fou ou dame).

---

# Tests Effectués

| Tests effectuées                                                                                                  | Résultat |
|-------------------------------------------------------------------------------------------------------------------|----------|
| Mettre le roi blanc en échec où le seule mouvement possible est l'attaque de la pièce blanche par une pièce noire |          |
| En Passant est uniquement pratiquable lorsque le pion adverse avance de deux cases                                |          |
| En passant est praticable uniquement au tour suivant et pas 2 tours après                                         |          |
| Le roque est uniquement praticable si le roi et la tour en question n'ont pas bougé                               |          |
| Le roque est pratiquable uniquement si les cases sur lesquelles passe le roi ne sont pas attaquées                |          |
| Les pions peuvent avancer de deux cases uniquement lors de leur premier déplacement                               |          |
| Chaque pièce avance dans la bonne direction                                                                       |          |
| Uniquement les chevaux peuvent sauter des pièces                                                                  |          |
| Les pièces ne peuvent pas découvrir un échec                                                                      |          |
| Le roi ne peut pas se mettre en échec                                                                             |          |
| Lorsque le roi est en échec, uniquement les mouvements de défenses sont pratiquables                              |          |

---

# Extensions

L'implémentation étend les fonctionnalités au-delà des exigences de base:

- **Logique Réutilisable:** La génération des mouvements est abstraite dans des
  classes réutilisables, simplifiant les extensions et les futures modifications.
- **Gestion des États de Jeu:** La détection de l'échec et mat et du pat
  améliore l'expérience utilisateur et respecte les règles réelles des échecs.

## Génération des Mouvements

La hiérarchie `MoveGenerator` encapsule la logique de génération des mouvements:

- **`DirectionalGenerator`:** Pour les mouvements linéaires (par exemple, tour,
  fou).
- **`KnightGenerator`:** Pour les mouvements en L propres aux cavaliers.
- **`DistanceGenerator`:** Gère les mouvements avec des portées variables, comme
  les pions.

## Gestion des États de Jeu

TODO

\newpage

# Conclusion

Ce projet a renforcé les principes de programmation orientée objet tout en
abordant des règles et interactions complexes. Les défis ont inclus:

- Garantir l'encapsulation tout en gérant les comportements variés des pièces.
- Traiter les cas limites dans les coups spéciaux et les conditions de fin de
  jeu.

Améliorations futures possibles:

- Ajouter une IA pour un mode solo.
- Proposer des suggestions de mouvements ou mettre en évidence les
  mouvements valides pour améliorer l’expérience utilisateur.

\newpage
\appendix

# Annexes

## Listing Java

c.f. page suivante.
