public enum NotesFrequenciesMapping {
    /*
        This is a notes-to-frequencies mapping table.
        All constant in this enum except for pause are named after <Note_Level> format.
     */

    //Level 2
    C_2(65), CSharp_2(69), D_2(73), DSharp_2(78), E_2(82), F_2(87), FSharp_2(93), G_2(98), GSharp_2(104), A_2(110),
    ASharp_2(117), B_2(124),

    //Level 3
    C_3(131), CSharp_3(139), D_3(147), DSharp_3(156), E_3(165), F_3(175), FSharp_3(185), G_3(196), GSharp_3(208), A_3(220),
    ASharp_3(233), B_3(247),

    //Level 4
    C_4(262), CSharp_4(278), D_4(294), DSharp_4(311), E_4(330), F_4(349), FSharp_4(370), G_4(392), GSharp_4(415), A_4(440),
    ASharp_4(466), B_4(494),

    //Level 5
    C_5(523), CSharp_5(554), D_5(587), DSharp_5(622), E_5(659), F_5(699), FSharp_5(740), G_5(784), GSharp_5(831), A_5(880),
    ASharp_5(932), B_5(988),

    //others
    PAUSE(1)
    ;

    final int frequency;
    NotesFrequenciesMapping(int frequency){this.frequency = frequency;}
}
