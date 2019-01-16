package com.sword.core.http.tool;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileItem {

    private String fileName;
    private String mimeType = "application/octet-stream";
    private byte[] content;
}
