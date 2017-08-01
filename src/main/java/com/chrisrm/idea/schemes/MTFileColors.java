package com.chrisrm.idea.schemes;

import com.chrisrm.idea.MTConfig;
import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.FileStatusFactory;
import com.intellij.ui.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public final class MTFileColors {
  public static final ColorKey NOT_CHANGED_IMMEDIATE = ColorKey.createColorKey("NOT_CHANGED_IMMEDIATE", ColorUtil.fromHex("#80CBC4"));
  public static final ColorKey NOT_CHANGED_RECURSIVE = ColorKey.createColorKey("NOT_CHANGED_RECURSIVE", ColorUtil.fromHex("#80CBC4"));
  public static final ColorKey DELETED = ColorKey.createColorKey("DELETED", ColorUtil.fromHex("#F77669"));
  public static final ColorKey MODIFIED = ColorKey.createColorKey("MODIFIED", ColorUtil.fromHex("#80CBC4"));
  public static final ColorKey ADDED = ColorKey.createColorKey("ADDED", ColorUtil.fromHex("#C3E887"));
  public static final ColorKey MERGE = ColorKey.createColorKey("MERGE", ColorUtil.fromHex("#C792EA"));
  public static final ColorKey UNKNOWN = ColorKey.createColorKey("UNKNOWN", ColorUtil.fromHex("#F77669"));
  public static final ColorKey IGNORED = ColorKey.createColorKey("IGNORED", ColorUtil.fromHex("#B0BEC5"));
  public static final ColorKey HIJACKED = ColorKey.createColorKey("HIJACKED", ColorUtil.fromHex("#FFCB6B"));
  public static final ColorKey MERGED_WITH_CONFLICTS = ColorKey.createColorKey("MERGED_WITH_CONFLICTS", ColorUtil.fromHex("#BC3F3C"));
  public static final ColorKey MERGED_WITH_BOTH_CONFLICTS = ColorKey.createColorKey("MERGED_WITH_BOTH_CONFLICTS", ColorUtil.fromHex
      ("#BC3F3C"));
  public static final ColorKey MERGED_WITH_PROPERTY_CONFLICTS = ColorKey.createColorKey("MERGED_WITH_PROPERTY_CONFLICTS", ColorUtil
      .fromHex("#BC3F3C"));
  public static final ColorKey DELETED_FROM_FS = ColorKey.createColorKey("DELETED_FROM_FS", ColorUtil.fromHex("#626669"));
  public static final ColorKey SWITCHED = ColorKey.createColorKey("SWITCHED", ColorUtil.fromHex("#F77669"));
  public static final ColorKey OBSOLETE = ColorKey.createColorKey("OBSOLETE", ColorUtil.fromHex("#FFCB6B"));
  public static final ColorKey SUPPRESSED = ColorKey.createColorKey("SUPPRESSED", ColorUtil.fromHex("#3C3F41"));

  public static final HashMap<FileStatus, ColorKey> FILE_STATUS_COLOR_MAP;

  static {
    FILE_STATUS_COLOR_MAP = new HashMap<>(18);
    // TODO move into a properties file ?
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.NOT_CHANGED_IMMEDIATE, MTFileColors.NOT_CHANGED_IMMEDIATE);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.NOT_CHANGED_RECURSIVE, MTFileColors.NOT_CHANGED_RECURSIVE);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.DELETED, MTFileColors.DELETED);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.MODIFIED, MTFileColors.MODIFIED);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.ADDED, MTFileColors.ADDED);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.MERGE, MTFileColors.MERGE);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.UNKNOWN, MTFileColors.UNKNOWN);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.IGNORED, MTFileColors.IGNORED);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.HIJACKED, MTFileColors.HIJACKED);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.MERGED_WITH_CONFLICTS, MTFileColors.MERGED_WITH_CONFLICTS);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.MERGED_WITH_BOTH_CONFLICTS, MTFileColors.MERGED_WITH_BOTH_CONFLICTS);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.MERGED_WITH_PROPERTY_CONFLICTS, MTFileColors.MERGED_WITH_PROPERTY_CONFLICTS);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.DELETED_FROM_FS, MTFileColors.DELETED_FROM_FS);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.SWITCHED, MTFileColors.SWITCHED);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.OBSOLETE, MTFileColors.OBSOLETE);
    //    FILE_STATUS_COLOR_MAP.put(FileStatus.SUPPRESSED, MTFileColors.SUPPRESSED);

    // Load all registered file statuses and read their colors from the properties
    final FileStatus[] allFileStatuses = FileStatusFactory.getInstance().getAllFileStatuses();
    for (final FileStatus allFileStatus : allFileStatuses) {
      final Color originalColor = allFileStatus.getColor();
      final Color property = UIManager.getColor("material.file." + allFileStatus.getId().toLowerCase());

      Color color = property == null ? originalColor : property;

      if (color == null) {
        color = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
      }
      // Add to the map
      FILE_STATUS_COLOR_MAP.put(allFileStatus, ColorKey.createColorKey(allFileStatus.getId(), color));
    }

  }

  private MTFileColors() {
  }

  public static Color get(final FileStatus status) {
    final EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();

    if (status == FileStatus.MODIFIED) {
      return ColorUtil.fromHex(MTConfig.getInstance().getAccentColor());
    }

    final ColorKey colorKey = MTFileColors.FILE_STATUS_COLOR_MAP.get(status);
    if (colorKey != null) {
      return globalScheme.getColor(colorKey);
    }

    return globalScheme.getDefaultForeground();
  }
}
