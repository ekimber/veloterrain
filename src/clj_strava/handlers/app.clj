(ns clj-strava.handlers.app
  (:require [clj-strava.tmpls :as tmpl]
            [clojure.tools.logging :as log]
            [clj-strava.strava :as strava]
            [clj-strava.polyline :as poly]))

(defn access_token [req]
  ((:query-params req) "access_token"))

(defn show-landing [req]
  (tmpl/landing {:user-agent (get-in req [:headers "user-agent"])
                 :title "Velo Terrain"
                 :list ["list item 1"
                        "list item 2"]}))

(defn get-access-token [req]
  (-> req :params :access-token))

(defn with-token [req f]
  (f (get-access-token req)))

(defn show-user-home [req]
  (tmpl/user {:access-token (get-access-token req)}))

(defn activities [req]
  (with-token req strava/activities))

(defn show-activities [req]
  (tmpl/activities {:activities (activities req)
                    :access-token (get-access-token req)}))

(defn show-activity [id req]
  (let [activity (strava/activity id (get-access-token req))]
    (tmpl/activity (merge
                    activity
                    {:polyline (-> activity :map :polyline poly/decode vec)}))))

(poly/decode "c|ssHhhv[N@RERKNSTg@`@}AJs@B[?_@IkA@a@DeAViB^yAD]Dw@A}Cc@qILcGEUF_BBgDEQGOEYEAABK_@W]?MOs@DYM[S{AOc@UkACO@QKk@?_@CM?Se@uCCcCEe@GQ?WMk@Ac@O}@GsA?w@Ec@Mu@u@mCIMEUGUQwAEs@GS@ODA\\f@j@l@BANHp@Tb@JR[HURMJEp@Gb@Dx@QVMIQ@Cb@Qx@q@JEv@CvBNZA\\Cf@CbFk@d@If@S\\O\\I`BFp@FN?PCLKJQZaAd@}@HULu@FUn@iATc@ViAL]NSJC\\BbH|AvA`@HBDJ@JCHEFiAt@GL@T^tAHz@f@dMLt@LRNLNBhBDP?PENG`@_@hB{BpCmAXG\\@JBh@XpA^r@XtAz@bDfBj@b@jD|Ct@v@hAvARNh@VzAV@HCBIA_@OeAWYMkAqAICY@cANs@EYGo@Sk@_@]Oo@MeAYMBCPFNDTHt@?j@H`B@nBEbA?b@PjA`@p@Nb@b@dC^z@FRHl@Dv@LjA?XEf@I\\GLEPA\\@v@`@hDNz@~@fBPd@b@p@z@v@HRNNT`@l@RLNBJJFDNJbA@^Gr@?d@Gd@IPID{@jA_@\\MJCFW^Ub@Sh@Kt@Bn@R|AXrDJxDV~DJbFDJXLn@D~@A~AMtB[pFo@b@?bDp@|ANpDQd@It@UlAq@vCmBlAo@^]\\c@dAcBh@eA\\cAb@{@R_@^a@XMJ?NBNNNHLBL?HIJ_@Lq@J_DDc@Zu@b@k@j@SlCm@vC{@tAm@^K\\K`@EL@VCJBx@b@`AfAV^NN|@nA~@`APL`A^^XfJbEf@XPb@TtAFRJNHJ~@j@b@`@d@PPRLV\\j@JJF?VYp@c@P?\\RHBfAQlBE|@m@DQXWVOZIf@At@@XC\\MXSn@s@n@{@|AqCdEaGdBmC`ByBj@y@^s@Xy@|A{E^q@n@y@Ra@~@eAtBuBtCsDdAkADCDBCLgAv@OP{DxEa@`@QVkA|@u@x@aApA]x@oAdFY|@Uh@}ApBUNGHYh@aAlAS`@yBjDiB`CaCpD_@b@UNYHg@@w@Gs@HKDc@Xm@n@SHWDkADe@Fa@NI@UUOGKMOEUJSNWb@Qj@GHWXKBIFi@j@GBO@o@ESDWXg@r@o@b@kAp@w@p@u@|@q@n@UZu@vAi@lAc@xAe@jA]tAw@jC_@~@eA|AqA`Ai@n@]Zc@j@[XULYHk@F_@Pw@z@o@j@aBhA}@bAyBtBqAv@uCxB{@d@m@h@OHS?SFoE~BaDpAy@XcAj@cB~AsAfA{AxAuD|Ew@hAK^?PCHa@b@eApBy@pA[T[NyB`AuCxAk@^k@j@e@v@Uj@cAxEUlAi@~Ee@zCCVD`CUpCAf@Jb@JLp@b@j@VJNFNBR?RI^o@h@UJI@cABu@Qa@C}A?_BGgEc@eAYm@I{@BqAHWC_@Sk@o@cAgBSKGCQBm@R{AJOJMVSx@a@bAcA|AcAbAi@Zk@Rs@Jy@PQBQAoAYqCS]I]SQEMKG]AQAiAFiALeAXwAr@aCx@aDNa@JM|BwCrAyAR_@FSHk@FeDXmFToDHoC?i@CQQSo@Mg@OaDyAiAc@{Ba@]KUUKUGYi@qEGy@?iAJ{D@kAIoDGoAIiAgAaI[{C_@sEc@kCm@yCg@kBWo@CK@Wd@mABe@KkAUaB[wKW{GOmBOgAm@qCQeAQyA]_BQg@sAaDI[WsAIQIKw@]Q[CSCc@BkAAmBJ_DAyADaB@MBGDAFJQj@G`@AzCGdC@rBAtAJ|@FtBPjC?bBFj@?bA]`BANMv@Y`C?n@DvAAXOt@IVERYf@?JQ`@C\\M?ONSAe@]_@Ik@\\a@Nq@Fk@NwA|@WTW\\MJS^Up@q@zAg@`BEd@JxCCVKNOBgE_AcAQO@MIAIJCR?xCx@jAb@Z@LEJODUAcCDg@f@wA~@{Bd@{@r@w@`@]t@c@NG`@GdAIVM^WHAPB")
