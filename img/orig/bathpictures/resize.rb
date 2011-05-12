Dir["*.jpg"].each do |file|
  `mogrify -resize 60% -quality 80% -write out/#{file} #{file}`
end
