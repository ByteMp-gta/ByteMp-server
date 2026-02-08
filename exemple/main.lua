function namef(player, damage)
    print("----------")
    print("O player tomou dano  " ..player)
    print ("dano:"..damage)
    print("----------")
end
addEvent("onPlayerDamage", namef)