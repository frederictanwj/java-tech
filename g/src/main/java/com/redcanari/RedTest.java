package com.redcanari;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RedTest {

	public static void main(String[] args) throws IOException {

		System.out.format(
				"use like:\njava com.redcanari.RedTest <target directory>\nThe statistic.txt beside your directory maintains the creation time, last access time and last modified time.\nThe file name delta<time> maintains the change of your directory.");
		if (args.length != 1) {
			System.exit(1);
		}
		String src = args[0];
		List<String> stat = new ArrayList<>(128);
		List<String> delta = new ArrayList<>(128);
		Map<WatchKey, Path> keyPath = new HashMap<>(128);
		Path srcPath = Paths.get(src);
		WatchService ws = FileSystems.getDefault().newWatchService();

		// depth-first iterate files
		Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
				statistic(path, attrs);

				// register the dir to watch service to monitor delta
				keyPath.put(path.register(ws, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY), path);
				return super.preVisitDirectory(path, attrs);
			}

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				// ignore MAC OS (.)DS_Store file
				if (path.getFileName().toString().toUpperCase().contains("DS_STORE")) {
					return FileVisitResult.CONTINUE;
				}
				statistic(path, attrs);

				return super.visitFile(path, attrs);
			}

			private void statistic(Path path, BasicFileAttributes attrs) {
				// statistic
				stat.add(path.toAbsolutePath().toString());
				stat.add(String.format("creation time: %s", attrs.creationTime().toString()));
				stat.add(String.format("last access time time: %s", attrs.lastAccessTime().toString()));
				stat.add(String.format("last modified time: %s\n", attrs.lastModifiedTime().toString()));
			}

		});

		// write down statistic
		Files.write(srcPath.resolveSibling("statistic.txt"), stat);

		// monitor change
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			try {
				for (WatchKey key = ws.poll(); key != null; key = ws.poll()) {
					Path path = keyPath.get(key);
					if (path != null) {
						for (WatchEvent<?> e : key.pollEvents()) {

							@SuppressWarnings("unchecked")
							WatchEvent<Path> event = (WatchEvent<Path>) e;

							Kind<Path> k = event.kind();
							if (StandardWatchEventKinds.OVERFLOW.equals(k)) {
								continue;
							}

							// delta
							Path realPath = path.resolve(event.context());
							delta.add(String.format("%s: %s", k, realPath));

							// if created a new dir, then register all sub-dir
							if (Files.isDirectory(realPath, LinkOption.NOFOLLOW_LINKS)) {
								Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {

									@Override
									public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs)
											throws IOException {

										keyPath.put(path.register(ws, StandardWatchEventKinds.ENTRY_CREATE,
												StandardWatchEventKinds.ENTRY_DELETE,
												StandardWatchEventKinds.ENTRY_MODIFY), path);
										return super.preVisitDirectory(path, attrs);
									}

								});
							}
						}
					}
					key.reset();
				}
				Files.write(srcPath.resolveSibling("delta." + LocalDateTime.now()), delta);
				delta.clear();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}, 0, 1, TimeUnit.MINUTES);

	}

}
