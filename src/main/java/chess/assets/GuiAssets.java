package chess.assets;

import chess.PieceType;
import chess.PlayerColor;
import chess.views.gui.GUIView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GuiAssets {
  public static void loadAssets(GUIView view) {
    try {
      view.registerResource(PieceType.ROOK, PlayerColor.BLACK, GUIView.createResource(assetsImage("cburnett/bR.png")));
      view.registerResource(PieceType.ROOK, PlayerColor.WHITE, GUIView.createResource(assetsImage("cburnett/wR.png")));

      view.registerResource(PieceType.PAWN, PlayerColor.BLACK, GUIView.createResource(assetsImage("cburnett/bP.png")));
      view.registerResource(PieceType.PAWN, PlayerColor.WHITE, GUIView.createResource(assetsImage("cburnett/wP.png")));

      view.registerResource(PieceType.KNIGHT, PlayerColor.BLACK,
          GUIView.createResource(assetsImage("cburnett/bN.png")));
      view.registerResource(PieceType.KNIGHT, PlayerColor.WHITE,
          GUIView.createResource(assetsImage("cburnett/wN.png")));

      view.registerResource(PieceType.BISHOP, PlayerColor.BLACK,
          GUIView.createResource(assetsImage("cburnett/bB.png")));
      view.registerResource(PieceType.BISHOP, PlayerColor.WHITE,
          GUIView.createResource(assetsImage("cburnett/wB.png")));

      view.registerResource(PieceType.QUEEN, PlayerColor.BLACK, GUIView.createResource(assetsImage("cburnett/bQ.png")));
      view.registerResource(PieceType.QUEEN, PlayerColor.WHITE, GUIView.createResource(assetsImage("cburnett/wQ.png")));

      view.registerResource(PieceType.KING, PlayerColor.BLACK, GUIView.createResource(assetsImage("cburnett/bK.png")));
      view.registerResource(PieceType.KING, PlayerColor.WHITE, GUIView.createResource(assetsImage("cburnett/wK.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static BufferedImage assetsImage(String imageName) throws IOException {
    return ImageIO.read(GuiAssets.class.getResource("images/" + imageName));
  }
}
